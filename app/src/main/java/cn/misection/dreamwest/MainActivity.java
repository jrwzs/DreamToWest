package cn.misection.dreamwest;

import android.os.Bundle;

import cn.misection.dreamwest.screen.TitleScreen;

import org.loon.framework.android.game.LGameAndroid2DActivity;
import org.loon.framework.android.game.LMode;
import org.loon.framework.android.game.core.LSystem;
import org.loon.framework.android.game.core.graphics.LFont;
import org.loon.framework.android.game.core.graphics.Screen;
import org.loon.framework.android.game.media.AssetsSound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gongdewei
 * Date: 12-3-18
 * Time: 下午6:59
 */
public class MainActivity extends LGameAndroid2DActivity {

    private volatile static MainActivity instance;

    public static final float NORMAL_SPEED = 0.13f;
    //    public static final float NORMAL_SPEED = 0.12f;
//    public static final float BEVEL_SPEED = 0.071f;
    public static final int STEP_DISTANCE = 20;

    private final static float TARGET_HEAP_UTILIZATION = 0.99f;

    private final static int CWJ_HEAP_SIZE = 5 * 1024 * 1024;

    public static LFont DEFAULT_FONT = LFont.getFont(LSystem.FONT_NAME, 0, 16);

    private static boolean debug;

    private static AssetsSound backgroundSound;

    private static AssetsSound effectSound;

    private final Map<String, Object> screenStatus = new HashMap<String, Object>();

    private Screen screen;

    private Screen lastScreen;

    public MainActivity() {
        // FIXME: 2021/9/4 这一句离谱语句不能少;
        // FIXME: 2021/9/4 离谱的是 构造还鼻血为 public;
        instance = this;
    }

    public static MainActivity getInstance() {
        if (instance == null) {
            synchronized (MainActivity.class) {
                if (instance == null) {
                    instance = new MainActivity();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        try {
            Class<?> vmRumTimeClass;
            vmRumTimeClass = Class.forName("dalvik.system.VMRuntime");
            Object runtime = vmRumTimeClass.getMethod("getRuntime").invoke(null);
            vmRumTimeClass.getMethod("setTargetHeapUtilization", Float.TYPE).invoke(runtime, TARGET_HEAP_UTILIZATION);
            vmRumTimeClass.getMethod("setMinimumHeapSize", Long.TYPE).invoke(runtime, CWJ_HEAP_SIZE);
        } catch (Exception e) {
            throw new RuntimeException("设置VM出错！");
        }
    }

    @Override
    public void onMain() {
        this.maxScreen(640, 480);
        this.initialization(true, LMode.FitFill);
        this.setShowLogo(false);
        this.setShowFPS(true);
        this.setShowMemory(true);
        this.setScreen(new TitleScreen());
        //this.setScreen(new SceneScreen(this));
        this.showScreen();
    }

    public static void playEffectSound(String filename) {
        System.err.println("playEffectSound: " + filename);
        if (effectSound == null) {
            effectSound = new AssetsSound(filename);
        }
        //TODO 运行同时播放多种音效
        try {
            effectSound.reset();
            effectSound.setDataSource(filename);
            effectSound.play(filename);
        } catch (Exception e) {
            System.err.println("播放音效失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void playSound(String filename) {
        if (backgroundSound != null) {
            backgroundSound.stop();
            backgroundSound.release();
        }
        try {
            backgroundSound = new AssetsSound(filename);
            backgroundSound.loop();
        } catch (Exception e) {
            System.err.println("播放音乐失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void stopSound() {
        if (backgroundSound != null) {
            backgroundSound.stop();
        }
    }

    public static void pauseSound() {
        if (backgroundSound != null) {
            backgroundSound.pause();
        }
    }

//    public void saveScreen(SceneScreen screen){
//        screenStatus.clear();
//        screenStatus.put("sceneId", screen.getSceneId());
//        screenStatus.put("sceneName", screen.getSceneName());
//        screenStatus.put("hero", screen.getHero().getData());
//        screenStatus.put("npclist", screen.getNpcStatusList());
//    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        MainActivity.debug = debug;
    }

    @Override
    public void onGameResumed() {

    }

    @Override
    public void onGamePaused() {

    }

    public Screen getScreen() {
        return screen;
    }

    @Override
    public void setScreen(Screen screen) {
        this.lastScreen = this.screen;
        this.screen = screen;
        super.setScreen(screen);
    }

    public void restoreScreen() {
        if (lastScreen != null) {
            this.setScreen(lastScreen);
        }
//        String sceneId = (String) this.screenStatus.get("sceneId");
//        String sceneName = (String) this.screenStatus.get("sceneName");
//        PlayerStatus herodata = (PlayerStatus) this.screenStatus.get("hero");
//        List<PlayerStatus> npclist = (List<PlayerStatus>) this.screenStatus.get("npclist");
//        if(sceneId != null){
//            SceneScreen screen = new SceneScreen(sceneId,sceneName, herodata, npclist);
//            Screen lastScreen = this.screen;
//            this.setScreen(screen);
//            lastScreen.dispose();
//        }
//        screenStatus.clear();
    }

    public void destroyLastScreen() {
        if (lastScreen != null) {
            lastScreen.destroy();
            lastScreen = null;
        }
    }
}
