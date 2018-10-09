import config from '../fixtures/config.js'

const loginUser = (email, password) => {
  cy.visit(config.devHostAddress)
  cy.get('#sign-in').click()
  cy.get('#email-address-input').type(email)
  cy.get('#email-password-input').type(password)
  cy.get('#email-sign-in').click()
}

const profilePageLinkId = '#profile-page-link'
const profilePageHeader = '#profile-page-header'
const profilePageUsernameId = '#profile-page-username'
const profilePageDescriptionId = '#profile-page-description'

describe('Logged in user', () => {

  beforeEach(() => {
    loginUser('nakki@noemail.com', 'nakkitest')
  })

  it('can see profile page link', () => {
    cy.get(profilePageLinkId).should('be.visible')
  })

  it('can access profile page', () => {
    cy.get(profilePageLinkId).click()
    cy.get(profilePageHeader).should('be.visible')
  })
})

describe('Profile page', () => {

  beforeEach(() => {
    loginUser('nakki@noemail.com', 'nakkitest')
    cy.get(profilePageLinkId).click()
  })

  it('contains username', () => {
    cy.get(profilePageUsernameId).should('be.visible')
  })

  it('contains user description', () => {
    cy.get(profilePageDescriptionId).should('be.visible')
  })
})
