describe('Login', () => {

    it('Login as doctor', () => {

        cy.server();
        cy.route('POST', `${Cypress.env('CYPRESS_API_BASE_URL')}/oauth/token`, 'fixture:modules/user/doctor_login.json');
        cy.route('GET', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/me', 'fixture:modules/user/doctor_me.json`);
        cy.route('GET', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/notification/push`, 'fixture:modules/notification/empty_push.json');

        cy.visit('/login');
        cy.location('pathname').should('eq', '/login');

        cy.get('[data-test-id="username__input"]')
            .type('doctor@example.com');
        cy.get('[data-test-id="password__input"]')
            .type('q1w2e3');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/analyse');
    });

});