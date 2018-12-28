package com.nugget.flappywings.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nugget.flappywings.FlappyBird;
import com.nugget.flappywings.sprites.Bird;
import com.nugget.flappywings.sprites.Tube;


public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private Bird bird;
    private Tube tube;
    private Texture background;
    private Texture ground;
    private Vector2 groundpos1,groundpos2;
    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        tube = new Tube(100);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundpos1 = new Vector2(cam.position.x - cam.viewportWidth/2,0);
        groundpos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(),0);
        cam.setToOrtho(false, (FlappyBird.WIDTH/2),(FlappyBird.HEIGHT/2));

        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i* (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(int i = 0;i < tubes.size;i++){
            Tube tube = tubes.get(i);
            if((cam.position.x - (cam.viewportWidth/2)) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING)* TUBE_COUNT));
            }
            if(bird.isBirdAlive()) {
                if (tube.collides(bird.getBounds())) {
                    bird.death();
                }
            }
        }
        updateGround();
        if(bird.isBirdAlive()){
            if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
                bird.death();
            }
        }else if(!bird.isBirdAlive()){
            if(!bird.isMusicPlaying()) {
                gsm.set(new PlayState(gsm));
            }
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,cam.position.x - (cam.viewportWidth/2),0,cam.viewportWidth,cam.viewportHeight);
        for(Tube tube:tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground,groundpos1.x,GROUND_Y_OFFSET);
        sb.draw(ground,groundpos2.x,GROUND_Y_OFFSET);
        if(bird.isBirdAlive()){
            sb.draw(bird.getTexture(),bird.getPosition().x,bird.getPosition().y);
        }
        else if(!bird.isBirdAlive()) {
            sb.draw(bird.getDeathTexture(), bird.getPosition().x, bird.getPosition().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        for(Tube tube : tubes){
           tube.dispose();
        }
        ground.dispose();
        System.out.println("playstate disposed");
    }
    private void updateGround(){
        if(cam.position.x - cam.viewportWidth /2 > groundpos1.x + ground.getWidth()){
            groundpos1.add(ground.getWidth() * 2, 0);
        }
        if(cam.position.x - cam.viewportWidth /2 > groundpos2.x + ground.getWidth()){
            groundpos2.add(ground.getWidth() * 2, 0);
        }
    }
}
