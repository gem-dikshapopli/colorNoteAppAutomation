Feature: Notepad App Functionality

  Scenario: Open Notepad Application
    Given the user opens the Notepad application

  Scenario: Create Text Notes
    When the user adds and saves multiple text notes
    Then verify if the notes are created or not

  Scenario: Edit the Existing Note
    When the user edit the existing note
    Then verify if the title gets edited

  Scenario: Delete the Note
    When the user deleted the note
    Then verify if the note is deleted successfully