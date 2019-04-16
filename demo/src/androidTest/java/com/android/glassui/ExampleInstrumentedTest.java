package com.android.glassui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rokid.glass.ui.util.RokidSystem;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.glassui", appContext.getPackageName());
    }

    @Test
    public void testAlignment() {
        Rect previewRect = new Rect(776, 430, 900, 554);
        Rect expectRect = new Rect(1001, 318, 1398, 713);

        Rect rect = RokidSystem.getAlignmentRect(1280, 720, previewRect);
        assertArrayEquals(new int[]{expectRect.left, expectRect.top, expectRect.right, expectRect.bottom},
                new int[]{rect.left, rect.top, rect.right, rect.bottom});
    }
}
