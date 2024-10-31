package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.messageService = new MessageService();
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
            
        app.patch("messages/{message_id}", null);
        app.get("accounts/{account_id}/messages", null);
        */

        app.post("register", this::postAccountHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postMessageHandler);

        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    // Register
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        // username is not blank, password is at least 4 characters long, Account with that username does not already exist
        if((account.getUsername() == "") || (account.getPassword().length() < 4)){
            ctx.status(400);
            addedAccount = null;
        }

        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    // Login
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

    // Post a Message
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    // Get All Messages
    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    // Get Message By Id
    public void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message != null){
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(200);
        }
    };

    // Delete Message By Id
    public void deleteMessageHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedmessage = messageService.deleteMessage(messageId);
        if(deletedmessage != null){
            ctx.json(mapper.writeValueAsString(deletedmessage));
        } else {
            ctx.status(200);
        }
    };

    // Update Message By Id
    public void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        //Message updatedmessage = messageService.updateMessage(messageId);
        Message message = mapper.readValue(ctx.body(), Message.class);
        
        if (message.getMessage_text() == null || message.getMessage_text().length() > 255 || message.getMessage_text().isEmpty()) {
            ctx.status(400);
            return;
        }

        Message updatedMessage = messageService.updateMessage(messageId, message.getMessage_text());
        if(updatedMessage != null){
            ctx.status(200).json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(400);
        }
    };

}