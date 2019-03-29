import root from "loglevel";
import Vue from "vue";
import Router from "vue-router";
import store from "./store/index";
import { types as userTypes } from "./store/modules/user";
import Home from "./views/Home.vue";

const log = root.getLogger("router");

Vue.use(Router);

const auth = {
  IS_ANONYMOUS: "IS_ANONYMOUS",
  PREMIT_ALL: "PREMIT_ALL",
  IS_AUTHENTIFICATED: "IS_AUTHENTIFICATED",
  HAS_PERMISSIONS: "HAS_PERMISSION",
  HAS_ANY_OF_PERMISSIONS: "HAS_ANY_OF_PERMISSIONS",
  HAS_ANY_PERMISSION: "HAS_ANY_PERMISSION"
};

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/home",
      name: "home",
      component: Home,
      meta: {
        auth: auth.HAS_ANY_PERMISSION
      }
    },
    {
      path: "/login",
      name: "about"
    },
    {
      path: "/403",
      name: "about"
    }
  ]
});

router.beforeEach((to, from, next) => {
  let result = 1;

  for (let record of to.matched) {
    if (
      record.meta.auth === auth.IS_ANONYMOUS &&
      store.getters[userTypes.IS_AUTHENTIFICATED]
    ) {
      result = 2;
    }

    if (
      record.meta.auth === auth.IS_AUTHENTIFICATED &&
      !store.getters[userTypes.IS_ANONYMOUS]
    ) {
      result = 0;
    }

    if (
      record.meta.auth === auth.HAS_PERMISSIONS &&
      record.meta.permissions != undefined &&
      record.meta.permissions.length > 0
    ) {
      if (!store.getters[userTypes.HAS_PERMISSIONS](record.meta.permissions)) {
        result = result > 0 ? -1 : result;
      }
    }

    if (
      record.meta.auth === auth.HAS_ANY_OF_PERMISSIONS &&
      record.meta.permissions != undefined &&
      record.meta.permissions.length > 0
    ) {
      if (
        !store.getters[userTypes.HAS_ANY_OF_PERMISSIONS](
          record.meta.permissions
        )
      ) {
        result = result > 0 ? -1 : result;
      }
    }

    if (
      record.meta.auth === auth.HAS_ANY_PERMISSION &&
      !store.getters[userTypes.HAS_ANY_PERMISSION]
    ) {
      result = result > 0 ? -1 : result;
    }
  }

  switch (result) {
    case 2:
      log.debug(`route ${to.path} not permited. allowed only for guest users`);
      next({
        path: "/home"
      });
      break;
    case 1:
      log.debug(`route ${to.path} permited`);
      next();
      break;
    case 0:
      log.debug(`route ${to.path} not permited. need authentification`);
      next({
        path: "/login",
        params: { nextUrl: to.fullPath }
      });
      break;
    case -1:
      log.debug(`route ${to.path} not permited. permissions are missing`);
      next({
        path: "/403"
      });
      break;
  }
});

export default router;
