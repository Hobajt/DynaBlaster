package com.kopecrad.dynablaster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.graphics.Point;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.MyPoint;
import com.kopecrad.dynablaster.game.MyRect;
import com.kopecrad.dynablaster.game.infrastructure.ImageResources;
import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.Block;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(Parameterized.class)

public class CollidableTest extends TestCase {

    @Mock
    private static ImageResources imgResMock;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {64, 64, 0},
                {32, 0, 1},
                {0, 32, 1},
                {32, 32, 1},
                {128, 128, 0},
        });
    }

    @Parameterized.Parameter(value = 0)
    public int offX;

    @Parameterized.Parameter(value = 1)
    public int offY;

    @Parameterized.Parameter(value = 2)
    public int collide;

    @Test
    public void Collidable_detectAndRepairCollision_test() {
        //System.out.println("====Running with " + offX + ", " + offY);
        ScreenSettings screen = new ScreenSettings();

        //Mockito.when(imgResMock.getGraphics("test")).thenReturn(null);

        //Whitebox.setInternalState(GameObject.class, "imgRes", imgResMock);
        Whitebox.setInternalState(GameObject.class, "screen", screen);

        Collidable a = new Block(1, 1, null);
        Collidable b = new Block(1, 1, null);

        b.setPosition(new MyPoint(offX, offY).add(screen.calcPosition(1, 1)));
        Point p = a.detectAndRepairCollision(b);

        if(collide == 0)
            assertNull(p);
        else
            assertNotNull(p);
    }
}
