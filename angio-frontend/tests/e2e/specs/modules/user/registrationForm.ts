describe('Registration', () => {

    it('Registration (success)', () => {

        cy.server();
        cy.route('POST', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/register`, 'fixture:modules/common/success.json');

        cy.visit('/register');
        cy.location('pathname').should('eq', '/register');

        cy.get('[data-test-id="email__input"]')
            .type('newUser@example.com');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/login');
    });

    it('Registration (fail)', () => {

        cy.server();
        cy.route({
            method: 'POST',
            url: `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/register`,
            status: 400
        });

        cy.visit('/register');
        cy.location('pathname').should('eq', '/register');

        cy.get('[data-test-id="email__input"]')
            .type('existsUser@example.com');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/register');
        cy.get('[data-test-id="formMsg__list"]')
            .children().should('have.length', 1);
    });
});
