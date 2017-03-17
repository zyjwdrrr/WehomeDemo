package com.microsoft.wehomedemo.ViewModels.Component;

import android.util.Log;

import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by v-chaojz on 3/17/2017.
 */

public class myTileOverlay {
    private TileProvider tileProvider = new UrlTileProvider(256,256) {
        @Override
        public URL getTileUrl(int x, int y, int zoom) {
            /* Define the URL pattern for the tile images */
            String s = String.format("http://tiles.aqicn.org/tiles/usepa-aqi/%d/%d/%d.png?token=7dfc3bc68fcc0b7a19162e4f1d6ba51ff747df04",
                    zoom, x, y);
            if (!checkTileExists(x, y, zoom)) {
                return null;
            }

            try {
                return new URL(s);
            } catch (MalformedURLException e) {
                throw new AssertionError(e);
            }
        }

        /*
         * Check that the tile server supports the requested x, y and zoom.
         * Complete this stub according to the tile range you support.
         * If you support a limited range of tiles at different zoom levels, then you
         * need to define the supported x, y range at each zoom level.
         */
        private boolean checkTileExists(int x, int y, int zoom) {
            int minZoom = 12;
            int maxZoom = 20;

            if ((zoom < minZoom || zoom > maxZoom)) {
                return false;
            }

            return true;
        }
    };

    public TileProvider getmyTile(){
        return tileProvider;
    }
}
