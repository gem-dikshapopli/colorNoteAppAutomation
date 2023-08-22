package definition;
import implementation.Implementation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.MalformedURLException;

public class StepDefinition {
    @Given("^the user opens the Notepad application$")
    public void openApplication() throws MalformedURLException {
        Implementation.launchApplication();
    }
    @When("^the user adds and saves multiple text notes$")
    public void createTextNotes() throws InterruptedException {
        Implementation.openAddTextScreen();
        Implementation.createMultipleTextNotes();
    }
    @Then("verify if the notes are created or not")
    public void verifyTheNotesCreated() {
        Implementation.verifyNotesCreated();
    }


    @When("^the user edit the existing note$")
    public void EditNote() throws InterruptedException {
        Implementation.editTheNote();
    }
    @Then("verify if the title gets edited")
        public void verifyTitleOfEditedNote(){
        Implementation.verifyEdit();
    }


    @When("the user deleted the note")
    public void deleteNote(){
        Implementation.deleteNotes();
    }
    @Then("verify if the note is deleted successfully")
    public void verifyDelete(){
        Implementation.verifyDeletedNote();
    }



}
