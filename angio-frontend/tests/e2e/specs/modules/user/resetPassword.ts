describe('Reset password', () => {

    it('Reset password (success)', () => {

        cy.server();
        cy.route('POST', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/reset`, 'fixture:modules/common/success.json');

        cy.visit('/user/reset');
        cy.location('pathname').should('eq', '/user/reset');

        cy.get('[data-test-id="email__input"]')
            .type('existsUser@example.com');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/user/reset');
    });

    it('Reset password (fail)', () => {

        cy.server();
        cy.route({
            method: 'POST',
            url: `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/reset`,
            status: 400
        });

        cy.visit('/user/reset');
        cy.location('pathname').should('eq', '/user/reset');

        cy.get('[data-test-id="email__input"]')
            .type('newUser@example.com');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/user/reset');
        cy.get('[data-test-id="formMsg__list"]')
            .children().should('have.length', 1);
    });
});
