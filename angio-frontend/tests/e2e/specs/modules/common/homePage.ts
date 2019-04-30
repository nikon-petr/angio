// https://docs.cypress.io/api/introduction/api.html
/// <reference types="cypress" />

describe('Home page', () => {

  it('Visits app as anonymous user', () => {
    cy.visit('/');
    cy.location('pathname').should('eq', '/landing')
  });

});
