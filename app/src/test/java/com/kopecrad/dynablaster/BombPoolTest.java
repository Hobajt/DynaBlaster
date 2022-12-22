package com.kopecrad.dynablaster;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;
import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.Bomb;
import com.kopecrad.dynablaster.game.objects.collidable.creature.BombPool;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BombPoolTest {

    @Mock
    private static ImageResources imgResMock;

    @Mock
    private static ScreenSettings screenMock;

    @Test
    public void BombPool_dropBomb_test() {
        Point testBomb_pos = new Point(1, 2);
        Point testBomb_outPos = new Point(testBomb_pos.x + 3, testBomb_pos.y + 4);

        Mockito.when(imgResMock.getGraphics("bomb_anim")).thenReturn(null);
        Mockito.when(screenMock.getClosestIndex(testBomb_outPos)).thenReturn(new Point(0, 0));
        Mockito.when(screenMock.calcPosition(0, 0)).thenReturn(new Point(1,1));
        Mockito.when(screenMock.calcPosition(testBomb_pos.x, testBomb_pos.y)).thenReturn(testBomb_outPos);

        Whitebox.setInternalState(GameObject.class, "imgRes", imgResMock);
        Whitebox.setInternalState(GameObject.class, "screen", screenMock);

        BombPool bombPool = new BombPool();

        //1st bomb attempt (should drop)
        Bomb bomb = bombPool.dropBomb(testBomb_pos);
        assertNotNull(bomb);
        assertTrue(bomb.isActive());

        //2nd bomb attempt (shouldn't drop)
        bomb = bombPool.dropBomb(testBomb_pos);
        assertNull(bomb);

        //3rd attempt (increased capacity; should drop)
        bombPool.updateCount(8);
        bomb = bombPool.dropBomb(testBomb_pos);
        assertNotNull(bomb);
        assertTrue(bomb.isActive());
    }

}
