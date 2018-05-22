describe('nakkivene', () => {
  it('venenakki', () => {
    cy.visit('http://localhost:3449')
//Setup the test data
//Firebase login is required at CLI
    cy.exec('firebase database:set /users cypress/fixtures/coffeepot.json --confirm')
    cy.get('#sign-in').click()
    const googleLogin = cy.get('#google-sign-in')
    const emailLogin = cy.get('#email-sign-in')
    emailLogin.contains('Kirjaudu sisään sähköposti:lla')
    emailLogin.click()
    cy.contains('Olisiko aika keittää kahvit?')
  })
})