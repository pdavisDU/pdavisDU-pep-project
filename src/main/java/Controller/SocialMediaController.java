package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     AccountService accountService;
     MessageService messageService;

     public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
     }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registrationHandler);
        // app.get("example-endpoint", this::exampleHandler);
        // app.post();
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.insertAccount(account);
        if(newAccount!=null){
            context.json(newAccount);
        }else{
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account login = accountService.loginAccount(account);
        if(login !=null){
            context.json(login);
        }else{
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage !=null){
            context.json(newMessage);
        }else{
            context.status(400);
        }
    }

    private void getMessagesHandler(Context context) {
        List<Message> messages = messageService.getMessages();
        context.json(messages);
    }

    private void getMessagesByIdHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message messageId = messageService.getMessagesById(id);
        if(messageId != null){
            context.json(messageId);
        } else {
            context.json("");
            context.status(200);
        }
    }

    private void getMessagesByUserHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(id);
        context.json(messages);
    }

    private void deleteMessageHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        if (messageService.getMessagesById(id) != null){
            context.json(messageService.getMessagesById(id));
            messageService.deleteMessage(id);
        } else {
            context.json("");
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message updateMessage = messageService.updateMessage(message, id);
        if(updateMessage !=null){
            context.json(updateMessage);
        }else{
            context.status(400);
        }
    }

}