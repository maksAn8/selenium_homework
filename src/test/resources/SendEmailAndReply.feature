Feature: Send email and reply

  Scenario: Send email and reply
    Given I open Main page
    And I get random email name
    And I select email domain
    Then I check that my domain is @rover.info
    And I open email settings and remember secret address
    Then I check than settings modal window is closed
    And I check info message "В ожидании новых писем..." on the Main Page
    And I send email to myself with a subject "Test" and secret address as email text
    Then I wait for message with sender: myself, subject: Test
    And I open this message and check that sender = myself, subject = Test and text = secret address
    And I reply on email with the text "Test2"
    Then I wait for message with sender: myself, subject: Re: Test
    And I open this message and check that sender = myself, subject = Re: Test and text = Test2
    And I delete this message
    Then I check message is deleted