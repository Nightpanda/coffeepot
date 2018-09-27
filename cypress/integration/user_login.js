import config from '../fixtures/config.js'

describe('User', () => {
    beforeEach(function () {
        cy.visit(config.devHostAddress)
    })

    it('login has options for google and email login', () => {
        cy.get('#sign-in').click()
        cy.get('#google-sign-in').should('be.visible')
        cy.get('#email-sign-in').should('be.visible')
    })

    it('logins with email', () => {
        cy.get('#sign-in').click()
        const emailInput = cy.get('#email-address-input')
        emailInput.type('nakki@noemail.com')
        const passwordInput = cy.get('#email-password-input')
        passwordInput.type('nakkivene')
        const emailSignIn = cy.get('#email-sign-in')
        emailSignIn.click()
        cy.contains('Olisiko aika keittää kahvit?')
        let welcomeMessage = ''
        cy.fixture('base-setup-database.json').then((coffeepotData) => {
            const userData = coffeepotData.users.Gu9kQ59kPtQFvRIVnnkABnxB3Iy1
            const username = userData.username
            const description = userData.description
            welcomeMessage = `Hei käyttäjä ${username} Kuvauksesi oli ${description}`
        })
        cy.get('#description-box').should('contain', welcomeMessage)
    })
})
