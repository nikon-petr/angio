describe('Login', () => {

    it('Reset password (success)', () => {

        cy.server();
        cy.route('POST', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/undefined/reset`, 'fixture:modules/user/reset_account.json');

        cy.visit('/reset');
        cy.location('pathname').should('eq', '/reset');

        cy.get('[data-test-id="resetCode__input"]')
            .type('123456');
        cy.get('[data-test-id="newPassword__input"]')
            .type('q1w2e3r4');
        cy.get('[data-test-id="newPasswordRepeat__input"]')
            .type('q1w2e3r4');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/login');
    });

    it('Reset password (fail)', () => {

        cy.server();
        cy.route({
            method: 'POST',
            url: `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/undefined/reset`,
            status: 400
        });

        cy.visit('/reset');
        cy.location('pathname').should('eq', '/reset');

        cy.get('[data-test-id="resetCode__input"]')
            .type('123456');
        cy.get('[data-test-id="newPassword__input"]')
            .type('q1w2e3r4t5');
        cy.get('[data-test-id="newPasswordRepeat__input"]')
            .type('q1w2e3r4t5');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/reset');
        cy.get('[data-test-id="formMsg__list"]')
            .children().should('have.length', 1);
    });
});