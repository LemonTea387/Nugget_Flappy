package com.nugget.flappywings.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nugget.flappywings.FlappyBird;
import com.nugget.flappywings.sprites.Bird;

public class PlayState extends State {
    private Bird bird;
    private Texture background;
    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        background = new Texture("bg.png");
        //cam.setToOrtho(false, (FlappyBird.WIDTH/2),(FlappyBird.HEIGHT/2));
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0,FlappyBird.WIDTH,FlappyBird.HEIGHT);
        sb.draw(bird.getTexture(),bird.getPosition().x,bird.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
