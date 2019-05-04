describe('Login', () => {

    it('Login as doctor (success)', () => {

        cy.server();
        cy.route('POST', `${Cypress.env('CYPRESS_API_BASE_URL')}/oauth/token`, 'fixture:modules/user/doctor_login.json');
        cy.route('GET', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/me`, 'fixture:modules/user/doctor_me.json');
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

    it('Login as doctor (fail)', () => {

        cy.server();
        cy.route({
            method: 'POST',
            url: `${Cypress.env('CYPRESS_API_BASE_URL')}/oauth/token`,
            status: 400,
            response: {
                "error" : "invalid_grant",
                "error_description" : "Bad credentials"
            }
        });

        cy.visit('/login');
        cy.location('pathname').should('eq', '/login');

        cy.get('[data-test-id="username__input"]')
            .type('doctor@example.com');
        cy.get('[data-test-id="password__input"]')
            .type('1');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/login');
        cy.get('[data-test-id="formMsg__list"]')
            .children().should('have.length', 1);
    });
});