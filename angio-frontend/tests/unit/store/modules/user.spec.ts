import Vue, {VueConstructor} from 'vue';
import Vuex, {Store} from 'vuex';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import ls from 'local-storage';
import flushPromises from 'flush-promises';
import {createLocalVue} from '@vue/test-utils';
import {cloneDeep} from 'lodash';
import {RootState, storeOptions} from '@/store/root';
import {
    authUser,
    hasAnyOfGivenPermissions,
    hasAnyPermission,
    hasPermissions,
    isAuthenticated,
    refreshAccessToken,
} from '@/store/modules/user';
import {UserCredentialsModel, UserInfoModel, UserPermission, UserSettingsModel} from '@/models/user';
import {Response, ResponseStatus} from '@/models/response';

describe('store/modules/user.js', () => {
    // mocks
    let axiosMock: MockAdapter;

    // data
    const accessToken: string = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJjMjhlMzMwOC00MTBiLTExZTktYjI' +
        '4ZC0wMjQyYWMxMzAwMDIiLCJzY29wZSI6WyJ0cnVzdCJdLCJleHAiOjE1NTM4NjAzMjAsImlhdCI6MTU1Mzg1OTQyMDEzMCwiYXV0aG9yaXR' +
        'pZXMiOlsiQU5BTFlTRV9FRElUIiwiUEFUSUVOVF9DUkVBVEUiLCJBTkFMWVNFX1JFTU9WRSIsIlBBVElFTlRfUkVNT1ZFIiwiQU5BTFlTRV9' +
        'DUkVBVEUiLCJBTkFMWVNFX0VYRUNVVEVfQUNUSU9OIiwiUEFUSUVOVF9WSUVXIiwiUFVTSF9OT1RJRklDQVRJT05fUkVDRUlWRSIsIlRPS0V' +
        'OX1JFVk9LRSIsIkFOQUxZU0VfVklFVyIsIlBBVElFTlRfRURJVCIsIklNQUdFX1VQTE9BRCJdLCJqdGkiOiIxNGQ2NzM3Ny01MDhmLTQ5ODQ' +
        'tOWIyNC03NjNiMmI3NmMyZGEiLCJjbGllbnRfaWQiOiJhbmdpby13ZWItY2xpZW50In0.kRycIjgtwVK2Tqd-bsQrhVC7isY0Ga4Sj94B1DT' +
        'rllo';
    const refreshToken: string = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJjMjhlMzMwOC00MTBiLTExZTktYj' +
        'I4ZC0wMjQyYWMxMzAwMDIiLCJzY29wZSI6WyJ0cnVzdCJdLCJhdGkiOiIxNGQ2NzM3Ny01MDhmLTQ5ODQtOWIyNC03NjNiMmI3NmMyZGEiLC' +
        'JleHAiOjE1NTQ0NjQyMjAsImlhdCI6MTU1Mzg1OTQyMDEzMCwiYXV0aG9yaXRpZXMiOlsiQU5BTFlTRV9FRElUIiwiUEFUSUVOVF9DUkVBVE' +
        'UiLCJBTkFMWVNFX1JFTU9WRSIsIlBBVElFTlRfUkVNT1ZFIiwiQU5BTFlTRV9DUkVBVEUiLCJBTkFMWVNFX0VYRUNVVEVfQUNUSU9OIiwiUE' +
        'FUSUVOVF9WSUVXIiwiUFVTSF9OT1RJRklDQVRJT05fUkVDRUlWRSIsIlRPS0VOX1JFVk9LRSIsIkFOQUxZU0VfVklFVyIsIlBBVElFTlRfRU' +
        'RJVCIsIklNQUdFX1VQTE9BRCJdLCJqdGkiOiIwNTkxOGI0NC05YWI2LTQyNDgtOTFhYy00OWRiNTU1ZDE5OTgiLCJjbGllbnRfaWQiOiJhbm' +
        'dpby13ZWItY2xpZW50In0.Etlhe2F2ASfiCuTghNclvj2Une1Rl0gwTKF5OdP6q4I';
    const credentials: UserCredentialsModel = {
        username: 'doctor@example.com',
        password: 'q1w2e3',
    };
    const user: Response<UserInfoModel> = {
        date: new Date(),
        status: ResponseStatus.SUCCESS,
        data: {
            id: 'c28e3308-410b-11e9-b28d-0242ac130002',
            email: 'doctor@example.com',
            firstname: 'Геннадий',
            lastname: 'Врачебный',
            patronymic: 'Aдреевич',
            permissions: [
                UserPermission.ANALYSE_VIEW,
                UserPermission.ANALYSE_CREATE,
                UserPermission.ANALYSE_EDIT,
                UserPermission.ANALYSE_EXECUTE_ACTION,
                UserPermission.ANALYSE_REMOVE,
                UserPermission.PATIENT_VIEW,
                UserPermission.PATIENT_CREATE,
                UserPermission.PATIENT_EDIT,
                UserPermission.PATIENT_REMOVE,
                UserPermission.IMAGE_UPLOAD,
                UserPermission.TOKEN_REVOKE,
                UserPermission.PUSH_NOTIFICATION_RECEIVE,
            ] as UserPermission[],
        },
    };
    const settings: Response<UserSettingsModel> = {
        date: new Date(),
        status: ResponseStatus.SUCCESS,
        data: {
            darkThemeEnabled: true,
            locale: 'ru',
        },
    };

    beforeAll(() => {
        axiosMock = new MockAdapter(axios);
        axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;
        ls.clear();
        axiosMock.reset();
    });

    afterEach(() => {
        ls.clear();
        axiosMock.reset();
    });

    it('givenLocalStorageDataWhenInitializeState', async () => {
        // given
        ls.set('accessToken', accessToken);
        ls.set('refreshToken', refreshToken);
        ls.set('id', user.data.id);
        ls.set('email', user.data.email);
        ls.set('firstname', user.data.firstname);
        ls.set('lastname', user.data.lastname);
        ls.set('patronymic', user.data.patronymic);
        ls.set('permissions', user.data.permissions);
        ls.set('darkThemeEnabled', settings.data.darkThemeEnabled);
        ls.set('locale', settings.data.locale);

        // when
        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // then
        expect(store.state.user.auth.accessToken).toBe(accessToken);
        expect(store.state.user.auth.refreshToken).toBe(refreshToken);
        expect(store.state.user.info.id).toBe(user.data.id);
        expect(store.state.user.info.email).toBe(user.data.email);
        expect(store.state.user.info.firstname).toBe(user.data.firstname);
        expect(store.state.user.info.lastname).toBe(user.data.lastname);
        expect(store.state.user.info.patronymic).toBe(user.data.patronymic);
        expect(store.state.user.info.permissions).toEqual(user.data.permissions);
        expect(store.state.user.settings).toEqual(settings.data);
    });

    it('givenCredentialsWhenAuthUser', async () => {
        // given
        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        axiosMock
            .onPost('/oauth/token')
            .reply(200, { access_token: accessToken, refresh_token: refreshToken })
            .onGet('/user/me')
            .reply(200, user)
            .onGet('/user/me/settings')
            .reply(200, settings);

        // when
        await authUser(store, credentials);

        // then
        await flushPromises();
        expect(isAuthenticated(store)).toBeTruthy();
        expect(store.state.user.auth.accessToken).toBe(accessToken);
        expect(store.state.user.auth.refreshToken).toBe(refreshToken);
        expect(store.state.user.info.id).toBe(user.data.id);
        expect(store.state.user.info.email).toBe(user.data.email);
        expect(store.state.user.info.firstname).toBe(user.data.firstname);
        expect(store.state.user.info.lastname).toBe(user.data.lastname);
        expect(store.state.user.info.patronymic).toBe(user.data.patronymic);
        expect(store.state.user.info.permissions).toEqual(user.data.permissions);
        expect(store.state.user.settings).toEqual(settings.data);

        expect(ls.get('accessToken')).toBe(accessToken);
        expect(ls.get('refreshToken')).toBe(refreshToken);
        expect(ls.get('id')).toBe(user.data.id);
        expect(ls.get('email')).toBe(user.data.email);
        expect(ls.get('firstname')).toBe(user.data.firstname);
        expect(ls.get('lastname')).toBe(user.data.lastname);
        expect(ls.get('patronymic')).toBe(user.data.patronymic);
        expect(ls.get('permissions')).toEqual(user.data.permissions);
        expect(ls.get('settings.darkThemeEnabled')).toEqual(
            settings.data.darkThemeEnabled,
        );
        expect(ls.get('settings.locale')).toEqual(settings.data.locale);
    });

    it('givenPermissionsWhenHasMatchingPermissionsThenTrue', () => {
        // given
        ls.set('permissions', user.data.permissions);

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasPermissions(store)([
            UserPermission.ANALYSE_VIEW,
            UserPermission.ANALYSE_CREATE,
        ]);

        // then
        expect(actual).toBeTruthy();
    });

    it('givenPermissionsWhenHasNotMatchingPermissionsThenFalse', () => {
        // given
        ls.set('permissions', user.data.permissions);

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasPermissions(store)([
            UserPermission.ANALYSE_VIEW,
            UserPermission.IMAGE_UPLOAD_PURGE_UNUSED,
        ]);

        // then
        expect(actual).toBeFalsy();
    });

    it('givenPermissionsWhenHasNoPermissionsThenFalse', () => {
        // given
        ls.set('permissions', user.data.permissions);

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasPermissions(store)([]);

        // then
        expect(actual).toBeFalsy();
    });

    it('givenPermissionsWhenHasAnyMatchingPermissionsThenTrue', () => {
        // given
        ls.set('permissions', user.data.permissions);

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasAnyOfGivenPermissions(store)([
            UserPermission.ANALYSE_VIEW,
            UserPermission.ANALYSE_CREATE,
        ]);

        // then
        expect(actual).toBeTruthy();
    });

    it('givenPermissionsWhenHasAnyMatchingPermissionsThenTrue', () => {
        // given
        ls.set('permissions', user.data.permissions);

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasAnyOfGivenPermissions(store)([
            UserPermission.ANALYSE_VIEW,
            UserPermission.IMAGE_UPLOAD_PURGE_UNUSED,
        ]);

        // then
        expect(actual).toBeTruthy();
    });

    it('givenPermissionsWhenHasAnyMatchingPermissionsThenTrue', () => {
        // given
        ls.set('permissions', user.data.permissions);

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasAnyPermission(store);

        // then
        expect(actual).toBeTruthy();
    });

    it('givenPermissionsWhenHasNoAnyMatchingPermissionsThenFalse', () => {
        // given
        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // when
        const actual = hasAnyPermission(store);

        // then
        expect(actual).toBeFalsy();
    });

    it('givenUserWhenRefreshTokenThenOk', async () => {
        // given
        ls.set('refreshToken', refreshToken);
        const refreshedAccessToken: string = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJjMjhlMzMwOC00MTBiLTExZTkt' +
            'YjI4ZC0wMjQyYWMxMzAwMDIiLCJzY29wZSI6WyJ0cnVzdCJdLCJleHAiOjE1NTM4NjAzMjAsImlhdCI6MTU1Mzg1OTQyMDEzMCwiYXV0aG' +
            '9yaXRpZXMiOlsiUEFUSUVOVF9WSUVXIl0sImp0aSI6IjE0ZDY3Mzc3LTUwOGYtNDk4NC05YjI0LTc2M2IyYjc2YzJkYSIsImNsaWVudF9p' +
            'ZCI6ImFuZ2lvLXdlYi1jbGllbnQifQ.w2QCLkebZSQUAh34K7WbRzQRTVpa-aazgBkWCjtfQVg';
        const newPermissions: UserPermission[] = [UserPermission.PATIENT_VIEW];

        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        axiosMock
            .onPost('/oauth/token')
            .reply(200, {
                access_token: refreshedAccessToken,
                refresh_token: refreshToken,
                token_type: 'bearer',
            });

        // when
        await refreshAccessToken(store);

        // then
        await flushPromises();
        expect(isAuthenticated(store)).toBeTruthy();
        expect(store.state.user.auth.accessToken).toBe(refreshedAccessToken);
        expect(store.state.user.auth.refreshToken).toBe(refreshToken);
        // expect(store.state.user.info.permissions.map((p) => p.name).sort()).toEqual(newPermissions.map((p) => p.name).sort());

        expect(ls.get('accessToken')).toBe(refreshedAccessToken);
        expect(ls.get('refreshToken')).toBe(refreshToken);
        // expect(ls.get('permissions').map((p) => p.name).sort()).toEqual(newPermissions.map((p) => p.name).sort());
    });
});
