package com.android.glassui;

import android.content.Context;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rokid.glass.ui.util.RokidSystem;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testAlignment2K() {
        Rect previewRect = new Rect(500, 500, 700, 700);

        Rect rect = RokidSystem.getAlignmentRect(1280, 720, previewRect);
        System.out.println(rect);
        Rect windowRect = RokidSystem.getWindowRect(1280, 720, rect);

        assertArrayEquals(new int[]{previewRect.left, previewRect.top, previewRect.right, previewRect.bottom},
                new int[]{windowRect.left, windowRect.top, windowRect.right, windowRect.bottom});
    }

    @Test
    public void testAlignment2KWin() {
        Rect previewRect = new Rect(718, 694 ,916, 965);

        Rect rect = RokidSystem.getAlignmentRect(2048, 1536, previewRect);
        System.out.println(rect);
        Rect windowRect = RokidSystem.getWindowRect(2048, 1536, rect);

        assertArrayEquals(new int[]{previewRect.left, previewRect.top, previewRect.right, previewRect.bottom},
                new int[]{windowRect.left, windowRect.top, windowRect.right, windowRect.bottom});
    }

    @Test
    public void testAlignmentView() {
        Rect rect = new Rect(0, 0, 384, 524);
        Rect windowRect = RokidSystem.getWindowRect(2048, 1536, rect);

        assertTrue(null != windowRect);
    }

    @Test
    public void testAlignmentHD() {
        Rect previewRect = new Rect(776, 430, 900, 554);
        Rect expectRect = new Rect(1001, 318, 1398, 713);

        Rect rect = RokidSystem.getAlignmentRect(1700, 720, previewRect);
        assertArrayEquals(new int[]{expectRect.left, expectRect.top, expectRect.right, expectRect.bottom},
                new int[]{rect.left, rect.top, rect.right, rect.bottom});
    }
}
