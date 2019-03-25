import Vue from "vue";
import Vuetify from "vuetify";
import { shallowMount } from "@vue/test-utils";
import HelloWorld from "@/components/HelloWorld.vue";

Vue.use(Vuetify);

describe("HelloWorld.vue", () => {
  it("HelloWorld component is visible", () => {
    const msg = "new message";

    const wrapper = shallowMount(HelloWorld, {
      propsData: { msg }
    });

    expect(wrapper.isVisible()).toBeTruthy();
  });
});
