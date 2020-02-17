// https://docs.cypress.io/api/introduction/api.html

describe('Home page redirection', () => {

  it('Visits app as anonymous user', () => {
    cy.visit('/');
    cy.location('pathname').should('eq', '/landing')
  });

});
