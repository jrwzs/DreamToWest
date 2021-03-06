package cn.misection.dreamwest.ui.activity;

import android.os.Bundle;
import android.view.Menu;

import cn.misection.dreamwest.scene.SceneHandler;
import cn.misection.dreamwest.ui.widget.UIHelper;

import org.loon.framework.android.game.LMode;

/**
 * Sprite 测试
 * Created by IntelliJ IDEA.
 * User: gongdewei
 * Date: 12-3-18
 * Time: 下午6:59
 */
public class SpriteActivity extends MainActivity {

    private final static float TARGET_HEAP_UTILIZATION = 0.99f;
    private final static int CWJ_HEAP_SIZE = 5 * 1024 * 1024;
    private static boolean isMainMenuOpen = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Class<?> vmRumTimeClass;
            vmRumTimeClass = Class.forName("dalvik.system.VMRuntime");
            Object runtime = vmRumTimeClass.getMethod("getRuntime").invoke(null);
            vmRumTimeClass.getMethod("setTargetHeapUtilization", Float.TYPE).invoke(runtime, TARGET_HEAP_UTILIZATION);
            vmRumTimeClass.getMethod("setMinimumHeapSize", Long.TYPE).invoke(runtime, CWJ_HEAP_SIZE);
        } catch (Exception e) {
            throw new RuntimeException("设置VM参数出错！");
        }
    }

    @Override
    public void onMain() {
        this.maxScreen(640, 480);
        this.initialization(true, LMode.FitFill);
        this.setShowLogo(false);
        this.setShowFPS(true);
        this.setShowMemory(true);
        this.setScreen(SceneHandler.createSceneWz());
        this.showScreen();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (false == isMainMenuOpen) {
            UIHelper.showDialog(this.getScreen(), UIHelper.MAIN_SYSTEM_MENU);
            isMainMenuOpen = true;
        } else {
            UIHelper.hideDialog(this.getScreen(), UIHelper.MAIN_SYSTEM_MENU);
            isMainMenuOpen = false;
        }
        return false;
    }

    @Override
    public void openOptionsMenu() {
        UIHelper.showDialog(this.getScreen(), UIHelper.MAIN_SYSTEM_MENU);
        isMainMenuOpen = true;
    }

    @Override
    public void closeOptionsMenu() {
        UIHelper.hideDialog(this.getScreen(), UIHelper.MAIN_SYSTEM_MENU);
        isMainMenuOpen = false;
    }

    @Override
    public void onGameResumed() {

    }

    @Override
    public void onGamePaused() {

    }

}
