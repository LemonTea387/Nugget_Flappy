package com.nugget.flappywings.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nugget.flappywings.FlappyBird;

public class MenuState extends State {
    private Texture background;
    private Texture playButton;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, (FlappyBird.WIDTH/2),(FlappyBird.HEIGHT/2));
        background = new Texture("bg.png");
        playButton = new Texture("playButton.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0);
        sb.draw(playButton,cam.position.x - playButton.getWidth()/2,cam.position.y );
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        System.out.println("MenuState disposed");
    }
}
