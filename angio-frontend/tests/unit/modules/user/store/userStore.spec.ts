import Vue, {VueConstructor} from 'vue';
import Vuex, {Store} from 'vuex';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import ls from 'local-storage';
import flushPromises from 'flush-promises';
import {createLocalVue} from '@vue/test-utils';
import {cloneDeep} from 'lodash';
import {RootState, storeOptions} from '@/store';
import {
    authUser,
    hasAnyOfGivenPermissions,
    hasAnyPermission,
    hasPermissions,
    isAuthenticated,
    refreshAccessToken,
} from '@/modules/user/store/userStore';
import {UserCredentialsModel, UserInfoModel} from '@/modules/user/models/user';
import {Response, ResponseStatus} from '@/modules/common/models/response';
import {Locale, UserPermission} from '@/modules/user/store/userState';

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
            fullName: {
                firstname: 'Геннадий',
                lastname: 'Врачебный',
                patronymic: 'Aдреевич',
            },
            organizationName: "Клиника глазных болезней СГМУ им. Разумовского",
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
            ],
            settings: {
                darkThemeEnabled: true,
                locale: Locale.RU,
            },
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
        ls.set('firstname', user.data.fullName.firstname);
        ls.set('lastname', user.data.fullName.lastname);
        ls.set('patronymic', user.data.fullName.patronymic);
        ls.set('organizationName', user.data.organizationName);
        ls.set('permissions', user.data.permissions);
        ls.set('darkThemeEnabled', user.data.settings.darkThemeEnabled);
        ls.set('locale', user.data.settings.locale);

        // when
        const localVue: VueConstructor<Vue> = createLocalVue();
        localVue.use(Vuex);
        const store: Store<RootState> = new Vuex.Store<RootState>(cloneDeep(storeOptions));

        // then
        expect(store.state.user.auth.accessToken).toBe(accessToken);
        expect(store.state.user.auth.refreshToken).toBe(refreshToken);
        expect(store.state.user.info.id).toBe(user.data.id);
        expect(store.state.user.info.email).toBe(user.data.email);
        expect(store.state.user.info.fullName.firstname).toBe(user.data.fullName.firstname);
        expect(store.state.user.info.fullName.lastname).toBe(user.data.fullName.lastname);
        expect(store.state.user.info.fullName.patronymic).toBe(user.data.fullName.patronymic);
        expect(store.state.user.info.organizationName).toBe(user.data.organizationName);
        expect(store.state.user.info.permissions).toEqual(user.data.permissions);
        expect(store.state.user.settings).toEqual(user.data.settings);
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
            .reply(200, user);

        // when
        await authUser(store, credentials);

        // then
        await flushPromises();
        expect(isAuthenticated(store)).toBeTruthy();
        expect(store.state.user.auth.accessToken).toBe(accessToken);
        expect(store.state.user.auth.refreshToken).toBe(refreshToken);
        expect(store.state.user.info.id).toBe(user.data.id);
        expect(store.state.user.info.email).toBe(user.data.email);
        expect(store.state.user.info.fullName.firstname).toBe(user.data.fullName.firstname);
        expect(store.state.user.info.fullName.lastname).toBe(user.data.fullName.lastname);
        expect(store.state.user.info.fullName.patronymic).toBe(user.data.fullName.patronymic);
        expect(store.state.user.info.organizationName).toBe(user.data.organizationName);
        expect(store.state.user.info.permissions).toEqual(user.data.permissions);
        expect(store.state.user.settings).toEqual(user.data.settings);

        expect(ls.get('accessToken')).toBe(accessToken);
        expect(ls.get('refreshToken')).toBe(refreshToken);
        expect(ls.get('id')).toBe(user.data.id);
        expect(ls.get('email')).toBe(user.data.email);
        expect(ls.get('firstname')).toBe(user.data.fullName.firstname);
        expect(ls.get('lastname')).toBe(user.data.fullName.lastname);
        expect(ls.get('patronymic')).toBe(user.data.fullName.patronymic);
        expect(ls.get('organizationName')).toBe(user.data.organizationName);
        expect(ls.get('permissions')).toEqual(user.data.permissions);
        expect(ls.get('darkThemeEnabled')).toEqual(
            user.data.settings.darkThemeEnabled,
        );
        expect(ls.get('locale')).toEqual(user.data.settings.locale);
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
        const refreshedAccessToken: string = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJjMjhlMzMwOC' +
            '00MTBiLTExZTktYjI4ZC0wMjQyYWMxMzAwMDIiLCJzY29wZSI6WyJ0cnVzdCJdLCJleHAiOjE1NTM4NjAzMjAsImlhdCI6MTU1Mz' +
            'g1OTQyMDEzMCwiYXV0aG9yaXRpZXMiOlsiUEFUSUVOVF9WSUVXIl0sImp0aSI6IjE0ZDY3Mzc3LTUwOGYtNDk4NC05YjI0LTc2M2' +
            'IyYjc2YzJkYSIsImNsaWVudF9pZCI6ImFuZ2lvLXdlYi1jbGllbnQifQ.w2QCLkebZSQUAh34K7WbRzQRTVpa-aazgBkWCjtfQVg';
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
        expect([...store.state.user.info.permissions].sort()).toEqual([...newPermissions]);

        expect(ls.get('accessToken')).toBe(refreshedAccessToken);
        expect(ls.get('refreshToken')).toBe(refreshToken);
        expect(ls.get('permissions').map((p: UserPermission) => p).sort()).toEqual(newPermissions.map((p) => p).sort());
    });
});
