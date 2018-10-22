import config from '../fixtures/config.js'

const idEmailSignUp = '#email-sign-up'
const idEmailAddressInput ='#email-address-input'
const idEmailPasswordInitialInput ='#email-password-initial-input'
const idEmailPasswordConfirmationInput ='#email-password-confirmation-input'
const idContinueRegistration = '#sign-up-continue'
const idCreateAccount = '#create-account'
const idUsernameInput = '#username-input'
const idAgreeUserTerms = '#agree-user-terms'

function create_UUID(){
    var dt = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (dt + Math.random()*16)%16 | 0;
        dt = Math.floor(dt/16);
        return (c=='x' ? r :(r&0x3|0x8)).toString(16);
    });
    return uuid;
}

describe('User signup', () => {
    beforeEach(() => {
        cy.visit(config.devHostAddress)
        cy.get('#sign-up').click()
    })

    it('has options for google and email login', () => {
        cy.get('#google-sign-up').should('be.visible')
        cy.get(idEmailSignUp).should('be.visible')
    });

    it('has inputs for email and password', () => {
        cy.get(idEmailSignUp).click()
        cy.get(idEmailAddressInput).should('be.visible')
        cy.get(idEmailPasswordInitialInput).should('be.visible')
        cy.get(idEmailPasswordConfirmationInput).should('be.visible')
    })

    it('continue does not activate when passwords do not match', () => {
        cy.get(idEmailSignUp).click()
        cy.get(idContinueRegistration).should('be.disabled')
        cy.get(idEmailAddressInput).type('testemail@noemail.com')
        cy.get(idEmailPasswordInitialInput).type('nakkinen')
        cy.get(idEmailPasswordConfirmationInput).type('venhonen')
        cy.get(idContinueRegistration).should('be.disabled')
    });

    it('continue activates after entering email address and both passwords', () => {
        cy.get(idEmailSignUp).click()
        cy.get(idContinueRegistration).should('be.disabled')
        cy.get(idEmailAddressInput).type('testemail@noemail.com')
        cy.get(idEmailPasswordInitialInput).type('nakkinen')
        cy.get(idEmailPasswordConfirmationInput).type('nakkinen')
        cy.get(idContinueRegistration).should('be.enabled')
    });

    it('registration information step activates only after a unique username is chosen and terms are accepted', () => {
        //Enter information phase
        cy.get(idEmailSignUp).click()
        cy.get(idEmailAddressInput).type(create_UUID() + '@noemail.com')
        const password = create_UUID()
        cy.get(idEmailPasswordInitialInput).type(password)
        cy.get(idEmailPasswordConfirmationInput).type(password)
        cy.get(idContinueRegistration).click()

        //Create account should disabled at enter
        cy.get(idCreateAccount).should('be.disabled')
        //Create account should be disabled with conflicting username
        cy.get(idUsernameInput).type('NakkiTest')
        cy.get(idAgreeUserTerms).click()
        cy.get(idCreateAccount).should('be.disabled')
        //Create account should  disabled with user terms not checked
        cy.get(idAgreeUserTerms).click()
        cy.get(idUsernameInput).clear()
        cy.get(idUsernameInput).type(create_UUID())
        cy.get(idCreateAccount).should('be.disabled')
        //Create account should be enabled with unique username and user terms checked
        cy.get(idAgreeUserTerms).click()
        cy.get(idCreateAccount).should('be.enabled')
    })

    it('creating an account moves the user to the application', () => {
        //Enter information phase
        cy.get(idEmailSignUp).click()
        cy.get(idEmailAddressInput).type(create_UUID() + '@noemail.com')
        const password = create_UUID()
        cy.get(idEmailPasswordInitialInput).type(password)
        cy.get(idEmailPasswordConfirmationInput).type(password)
        cy.get(idContinueRegistration).click()
        cy.get(idUsernameInput).type(create_UUID())
        cy.get(idAgreeUserTerms).click()
        cy.get(idCreateAccount).click()
        cy.contains('Olisiko aika keittää kahvit?')
    });

});
