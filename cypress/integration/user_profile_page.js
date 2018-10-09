import config from '../fixtures/config.js'

const loginUser = (email, password) => {
  cy.visit(config.devHostAddress)
  cy.get('#sign-in').click()
  cy.get('#email-address-input').type(email)
  cy.get('#email-password-input').type(password)
  cy.get('#email-sign-in').click()
}

describe('Logged in user', () => {

  beforeEach(() => {
    loginUser('nakki@noemail.com', 'nakkitest')
  })

  it('can see profile page link', () => {
    cy.get('#profile-page-link').should('be.visible')
  })

})
