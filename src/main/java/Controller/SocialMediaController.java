package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    //MessageService messageService;

    public SocialMediaController(){
        //this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        /**


        app.post("messages", null);
        
        app.get("messages/{message_id}", null);
        app.delete("messages/{message_id}", null);
        app.patch("messages/{message_id}", null);
        app.get("accounts/{account_id}/messages", null);
        */

        app.post("register", this::postAccountHandler);
       //app.post("login", null);

        // app.get("messages", this::getAllMessagesHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

    //    /** 
        // username is not blank, password is at least 4 characters long, Account with that username does not already exist
        if((account.getUsername() == "") || (account.getPassword().length() < 4)){
            ctx.status(400);
            addedAccount = null;
        }
    //    */
  

        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
    }

    /**
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loggedinAccount = mapper.readValue(ctx.body(), Account.class);
        Account account = accountService.verifyLogin(loggedinAccount.getUsername(), loggedinAccount.getPassword());

        if(account != null){
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }
    */

    /**
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), valueType:Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }
    */

    /** 
    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    */

}

// remove this later***
}