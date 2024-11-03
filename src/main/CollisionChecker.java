package main;

import entity.Entity;
import constants.Constants;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile (Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftColumn = entityLeftWorldX / gp.tileSize;
        int entityRightColumn = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNumber1, tileNumber2;

        switch (entity.direction) {
            case "up":
                // this predicts where the player will be that's why we use - speed (moving up)
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileManager.mapTileNumber[entityLeftColumn][entityTopRow];
                tileNumber2 = gp.tileManager.mapTileNumber[entityRightColumn][entityTopRow];

                // check if the tile we hit is a solid one
                if (gp.tileManager.tile[tileNumber1].isColliding || gp.tileManager.tile[tileNumber2].isColliding)
                {
                    entity.isColliding = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileManager.mapTileNumber[entityLeftColumn][entityBottomRow];
                tileNumber2 = gp.tileManager.mapTileNumber[entityRightColumn][entityBottomRow];

                if (gp.tileManager.tile[tileNumber1].isColliding || gp.tileManager.tile[tileNumber2].isColliding)
                {
                    entity.isColliding = true;
                }
                break;

            case "left":
                entityLeftColumn = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileManager.mapTileNumber[entityLeftColumn][entityTopRow];
                tileNumber2 = gp.tileManager.mapTileNumber[entityLeftColumn][entityBottomRow];

                if (gp.tileManager.tile[tileNumber1].isColliding || gp.tileManager.tile[tileNumber2].isColliding)
                {
                    entity.isColliding = true;
                }
                break;

            case "right":
                entityRightColumn = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileManager.mapTileNumber[entityRightColumn][entityTopRow];
                tileNumber2 = gp.tileManager.mapTileNumber[entityRightColumn][entityBottomRow];

                if(gp.tileManager.tile[tileNumber1].isColliding || gp.tileManager.tile[tileNumber2].isColliding) {
                    entity.isColliding = true;
                }
                break;
        }
    }

    // check if player collides with any object and if so, we return the object's index
    public int checkObject(Entity entity, boolean isEntityPlayer) {
        
        int index = Constants.EMPTY_AREA;
        // loop through object array
        for (int i = 0; i < gp.objects.length; i++) {
            if (gp.objects[i] != null) {
                // get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get object's solid area position
                gp.objects[i].solidArea.x = gp.objects[i].worldX + gp.objects[i].solidArea.x;
                gp.objects[i].solidArea.y = gp.objects[i].worldY + gp.objects[i].solidArea.y;

                switch (entity.direction) {
                    // after we moved the entity where will it be in the next pos
                    case "up" :
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            // check if object is a solid object
                            if (gp.objects[i].isColliding) {
                                entity.isColliding = true;
                            } // we need to check if the entity is the player because enemies can't pick up objects
                            if (isEntityPlayer) {
                                index = i; // and get which object we're colliding with
                            }
                        }
                        break;
                    case "down" :
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            if (gp.objects[i].isColliding) {
                                entity.isColliding = true;
                            }
                            if (isEntityPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "left" :
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            if (gp.objects[i].isColliding) {
                                entity.isColliding = true;
                            }
                            if (isEntityPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "right" :
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            if (gp.objects[i].isColliding) {
                                entity.isColliding = true;
                            }
                            if (isEntityPlayer) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objects[i].solidArea.x = gp.objects[i].solidAreaDefaultX;
                gp.objects[i].solidArea.y = gp.objects[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // NPC OR ENEMY COLLISION?
    public int checkEntity(Entity entity, Entity[] targets) {
        int index = Constants.EMPTY_AREA; // if no collision 999 is returned, otherwise the index of the colliding target
        // loop through targets array
        for (int i = 0; i < targets.length; i++) {
            if (targets[i] != null) {
                // get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get NPC's solid area position
                targets[i].solidArea.x = targets[i].worldX + targets[i].solidArea.x;
                targets[i].solidArea.y = targets[i].worldY + targets[i].solidArea.y;

                switch (entity.direction) {
                    // after we moved the entity where will it be in the next pos
                    case "up" :
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(targets[i].solidArea)) {
                            // all monsters and NPCs are solid
                            entity.isColliding = true;
                            index = i;
                        }
                        break;

                    case "down" :
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.isColliding = true;
                            index = i;
                        }
                        break;

                    case "left" :
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.isColliding = true;
                            index = i;
                        }
                        break;

                    case "right" :
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.isColliding = true;
                            index = i;
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                targets[i].solidArea.x = targets[i].solidAreaDefaultX;
                targets[i].solidArea.y = targets[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        // get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // get player's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            // after we moved the entity where will it be in the next pos
            case "up" :
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    // all monsters and NPCs are solid
                    entity.isColliding = true;
                    return true;
                }
                break;

            case "down" :
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.isColliding = true;
                    return true;
                }
                break;

            case "left" :
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.isColliding = true;
                    return true;
                }
                break;

            case "right" :
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.isColliding = true;
                    return true;
                }
                break;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return false;
    }
}
