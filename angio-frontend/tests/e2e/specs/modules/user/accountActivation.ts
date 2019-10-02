describe('Login', () => {

    it('Account activation (success)', () => {

        cy.server();
        cy.route('POST', `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/undefined/enable`, 'fixture:modules/user/account_activation.json');

        cy.visit('/activation');
        cy.location('pathname').should('eq', '/activation');

        cy.get('[data-test-id="enablingCode_input"]')
            .type('123456');
        cy.get('[data-test-id="firstname_input"]')
            .type('Name');
        cy.get('[data-test-id="lastname_input"]')
            .type('Lastname');
        cy.get('[data-test-id="patronymic_input"]')
            .type('Patronymic');
        cy.get('[data-test-id="newPassword_input"]')
            .type('q1w2e3r4');
        cy.get('[data-test-id="newPasswordRepeat_input"]')
            .type('q1w2e3r4');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/activation');

        cy.get('[data-test-id="login__button"]')
            .click();

        cy.location('pathname').should('eq', '/login');
    });

    it('Account activation (fail)', () => {

        cy.server();
        cy.route({
            method: 'POST',
            url: `${Cypress.env('CYPRESS_API_BASE_URL')}/api/v2/user/undefined/enable`,
            status: 400
        });

        cy.visit('/activation');
        cy.location('pathname').should('eq', '/activation');

        cy.get('[data-test-id="enablingCode_input"]')
            .type('123456');
        cy.get('[data-test-id="firstname_input"]')
            .type('Name');
        cy.get('[data-test-id="lastname_input"]')
            .type('Lastname');
        cy.get('[data-test-id="patronymic_input"]')
            .type('Patronymic');
        cy.get('[data-test-id="newPassword_input"]')
            .type('q1w2e3r4');
        cy.get('[data-test-id="newPasswordRepeat_input"]')
            .type('q1w2e3r4');

        cy.get('[data-test-id="submit__button"]')
            .click();

        cy.location('pathname').should('eq', '/activation');
        cy.get('[data-test-id="formMsg__list"]')
            .children().should('have.length', 1);
    });
});