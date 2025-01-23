package Controller;

import Service.AccountService;
import Service.MessageService;

import Model.Account;
import Model.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::postCreateUser);
        app.post("/login", this::postLoginUser);
        app.post("/messages", this::postCreateMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessageByUser);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postCreateUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedUser = accountService.addUser(account);
        if (addedUser != null) {
            ctx.json(mapper.writeValueAsString(addedUser));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginUser = accountService.loginUser(account);
        if (loginUser != null) {
            ctx.json(mapper.writeValueAsString(loginUser));
        } else {
            ctx.status(401);
        }
    }

    private void postCreateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            ctx.json(mapper.writeValueAsString(createdMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) {
        Message message = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    private void deleteMessage(Context ctx) {
        Message message = messageService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message ret = messageService.updateMessage(Integer.parseInt(ctx.pathParam("message_id")), message);
        if (ret != null) {
            ctx.json(ret);
        } else {
            ctx.status(400);
        }
    }

    private void getMessageByUser(Context ctx) {
        ctx.json(messageService.getAllMessagesByUserId(Integer.parseInt(ctx.pathParam("account_id"))));
    }
}