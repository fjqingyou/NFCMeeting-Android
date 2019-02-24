

package com.nfcmeeting;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class AppDaoGenerator {

    public static void main(String...args){
        Schema rootSchema = new Schema(5, "com.nfcmeeting.nfcmeeting.dao");
        addAuthUser(rootSchema);
        addMeeting(rootSchema);
        try {
            new DaoGenerator().generateAll(rootSchema, "/home/coderyellow/AndroidStudioProjects/NFCMeeting-Android/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * add auth user
     * @param schema
     */
    private static void addAuthUser(Schema schema){
        Entity entity = schema.addEntity("AuthUser");
        entity.addStringProperty("accessToken").primaryKey().notNull();
        entity.addDateProperty("authTime").notNull();
        entity.addIntProperty("expireIn").notNull();
        entity.addStringProperty("scope").notNull();
        entity.addBooleanProperty("selected").notNull();

        entity.addStringProperty("loginId").notNull();
        entity.addStringProperty("name");
        entity.addStringProperty("avatar");
    }

    private static void addMeeting(Schema schema) {
        Entity entity = schema.addEntity("Meeting");
        entity.addShortProperty("meetingId").primaryKey().notNull();
        entity.addStringProperty("title").notNull();
        entity.addStringProperty("content").notNull();
        entity.addStringProperty("beginTime").notNull();
        entity.addStringProperty("endTime").notNull();


    }
}
