package com.nugget.flappywings.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Animation deathAnimation;
    private Texture Nuggettexture;
    private Texture deathTexture;
    private Music death,deathRNG;
    private Random RNG;
    private boolean BirdAlive;

    public Bird(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        Nuggettexture = new Texture("nuggetanimation.png");
        deathTexture = new Texture("nuggetanimationtriggered.png");
        birdAnimation = new Animation(new TextureRegion(Nuggettexture),3,0.5f);
        deathAnimation = new Animation(new TextureRegion(deathTexture),3,0.1f);
        bounds = new Rectangle(x,y,Nuggettexture.getWidth()/3,Nuggettexture.getHeight());
        death = Gdx.audio.newMusic(Gdx.files.internal("deathsound.ogg"));
        deathRNG = Gdx.audio.newMusic(Gdx.files.internal("deathsoundRNG.ogg"));
        RNG = new Random();
        BirdAlive = true;
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        if(BirdAlive) {
            position.add(MOVEMENT * dt, velocity.y, 0);
            }else if(!BirdAlive){
            position.add(0,0,0);
            deathAnimation.update(dt);
        }
        if(position.y < 0){
            position.y = 0;

        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }
    public TextureRegion getDeathTexture(){
        return deathAnimation.getFrame();
    }
    public void jump(){
        velocity.y = 250;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        Nuggettexture.dispose();
        deathTexture.dispose();
        death.dispose();
        deathRNG.dispose();
    }
    public void death(){
        BirdAlive = false;
        int rng = RNG.nextInt(10);
        System.out.println(rng);
        if(rng == 1){
            deathRNG.play();
        }else{
            death.play();
        }
    }

    public boolean isBirdAlive() {
        return BirdAlive;
    }
    public boolean isMusicPlaying(){
        return death.isPlaying() || deathRNG.isPlaying();
    }
}
