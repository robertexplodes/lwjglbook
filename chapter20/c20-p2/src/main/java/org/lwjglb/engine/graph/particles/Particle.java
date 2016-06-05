package org.lwjglb.engine.graph.particles;

import org.joml.Vector3f;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Texture;
import org.lwjglb.engine.items.GameItem;

public class Particle extends GameItem {

    private long updateTextureMills;
    
    private long currentAnimTimeMillis;
    
    private Vector3f speed;

    /**
     * Time to live for particle in milliseconds.
     */
    private long ttl;

    private int animFrames;
    
    public Particle(Mesh mesh, Vector3f speed, long ttl, long updateTextureMills) {
        super(mesh);
        this.speed = new Vector3f(speed);
        this.ttl = ttl;
        this.updateTextureMills = updateTextureMills;
        this.currentAnimTimeMillis = 0;
        Texture texture = this.getMesh().getMaterial().getTexture();
        this.animFrames = texture.getNumCols() * texture.getNumRows();
    }

    public Particle(Particle baseParticle) {
        super(baseParticle.getMesh());
        Vector3f aux = baseParticle.getPosition();
        setPosition(aux.x, aux.y, aux.z);
        aux = baseParticle.getRotation();
        setRotation(aux.x, aux.y, aux.z);
        setScale(baseParticle.getScale());
        this.speed = new Vector3f(baseParticle.speed);
        this.ttl = baseParticle.geTtl();
        this.updateTextureMills = baseParticle.getUpdateTextureMills();
        this.currentAnimTimeMillis = 0;
        this.animFrames = baseParticle.getAnimFrames();
    }

    public int getAnimFrames() {
        return animFrames;
    }

    public Vector3f getSpeed() {
        return speed;
    }

    public long getUpdateTextureMills() {
        return updateTextureMills;
    }

    public void setSpeed(Vector3f speed) {
        this.speed = speed;
    }

    public long geTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public void setUpdateTextureMills(long updateTextureMills) {
        this.updateTextureMills = updateTextureMills;
    }
    
    /**
     * Updates the Particle's TTL
     * @param elapsedTime Elapsed Time in milliseconds
     * @return The Particle's TTL
     */
    public long updateTtl(long elapsedTime) {
        this.ttl -= elapsedTime;
        this.currentAnimTimeMillis += elapsedTime;
        if ( this.currentAnimTimeMillis >= this.getUpdateTextureMills() && this.animFrames > 0 ) {
            this.currentAnimTimeMillis = 0;
            int pos = this.getTextPos();
            pos++;
            if ( pos < this.animFrames ) {
                this.setTextPos(pos);
            } else {
                this.setTextPos(0);
            }
        }
        return this.ttl;
    }
    
}