package com.processisolation.rosera.p1_aad;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.processisolation.rosera.p1_aad.dummy.DummyContent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringBufferInputStream;

import static com.processisolation.rosera.p1_aad.BuildConfig.TMDB_API_KEY;
import static com.processisolation.rosera.p1_aad.R.drawable.movie_placeholder;

/**
 * A fragment representing a single Medium detail screen.
 * This fragment is either contained in a {@link MediumListActivity}
 * in two-pane mode (on tablets) or a {@link MediumDetailActivity}
 * on handsets.
 */
public class MediumDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
//    public static final String ARG_ITEM_ID = "item_id";
    private static final String API_KEY             = TMDB_API_KEY;
    private View mRootview;

    // TODO: Pass arguments across from Medium List Activity
    public static final String ARG_MEDIUM_ID        = "medium_id";
    public static final String ARG_MEDIUM_TITLE     = "medium_title";

    // Screen density settings
    private static final int DENSITY_280 = 280;
    private static final int DENSITY_480 = 480;
    private static final int DENSITY_570 = 570;

    private static final int TMDB_MOVIES    = 0;
    private static final int TMDB_TRAILERS  = 1;
    private static final int TMDB_REVIEWS   = 2;
    private static final int TMDB_SIMILAR   = 3;

    private String mID;
    private String mTitle;
    private String mRating;
    private String mPoster      = "";
    private String mYear        = "";
    private String mRuntime     = "";
    private String mThumbnail   = "";
    private String mOverview    = "";


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MediumDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Todo: Add other parameters
        if (getArguments().containsKey(ARG_MEDIUM_TITLE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            mTitle = getArguments().getString(ARG_MEDIUM_TITLE);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mTitle);
            }
        }

        if (getArguments().containsKey(ARG_MEDIUM_ID)) {
            // TODO: Add ID to search against the Film details
            mID = getArguments().getString(ARG_MEDIUM_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootview = inflater.inflate(R.layout.medium_detail, container, false);

        if (mRootview != null) {
            // Make a call to the TMDB
            onRequestMovieAPI(mID, TMDB_TRAILERS);
        }

        return mRootview;
    }


    /*
     * @Name: getScreenDensity
     * @return void
     *      "w92", "w154", "w185", "w342", "w500", "w780", or "original".
     * @Description: Check the screen density
     */

    public String getScreenDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int density = metrics.densityDpi;

        String  strScreenDensity;

        if (density < DENSITY_280)
            strScreenDensity = getResources().getString(R.string.media_density_185);
        else if (density < DENSITY_480)
            strScreenDensity = getResources().getString(R.string.media_density_342);
        else if (density < DENSITY_570)
            strScreenDensity = getResources().getString(R.string.media_density_500);
        else
            strScreenDensity = getResources().getString(R.string.media_density_780);

        return strScreenDensity;
    }



       /*
     * Name: onRequestMovieAPI
     * Comment: Access MovieAPI using the Volley library
     * Tasks:
     * 1. Check Network/Internet available
     * 2. Find "results" object and store it in an JSONArray
     * 3. Iterate through the JSONArray
     * 4.   Read item contents and add to the movie array
     * 5. Send a data set change notification to update the Adapter view
     * 6. Handle Exceptions
     *
     */

    private void onRequestMovieAPI(String mID, int searchType) {
        // TODO: Stage 2: mFilmAPI Query
        final String MOVIE_API_URI = getResources().getString(R.string.medium_tmdb_api);
        final String MOVIE_API_KEY = "?api_key=" + TMDB_API_KEY;
        final String MOVIE_API_VIDEO = "&append_to_response=videos";
        final String MOVIE_API_REVIEWS = "/reviews";
        final String MOVIE_API_SIMILAR = "/similar";
        String strQueryTMDB = "";


        // TODO: Make this a method
        switch (searchType){
            case TMDB_MOVIES: // Movie
                // Get the Sort Order preference

                // Store the api Query
//                strQueryTMDB =  MOVIE_API_URI+mSortOrder+MOVIE_API_KEY;
                break;
            case TMDB_TRAILERS: // Trailers
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_KEY+MOVIE_API_VIDEO;
                break;

            case TMDB_REVIEWS: // Reviews
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_REVIEWS+MOVIE_API_KEY;
                break;

            case TMDB_SIMILAR: // Similar
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_SIMILAR+MOVIE_API_KEY;
                break;

            default:
                break;
        }

        // TODO: sort_by=popularity.desc/popular
        // TODO: sort_by=vote_average.desc/top_rated

        // TODO: Alter setting - "w92", "w154", "w185", "w342", "w500", "w780", or "original".
        // TODO: Add a setting to allow the user to select image size
        final String MOVIE_IMAGE_URI = getResources().getString(R.string.medium_image_uri);

    /*
     *  JSON Request - Volley JSON example
     */

        // TODO: Request movie information based on ID passed from MovieFragment

        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, strQueryTMDB, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // Remove the contents
//                            mYouTubeTrailers.clear();

                            // TODO: Make this a method
                            JSONObject videos = response.getJSONObject("videos");
                            JSONArray jsonArray = videos.getJSONArray("results");

                            // TODO: Allocate memory based on number of available trailers

                            // IF Videos are required
//
                            // TODO: Change these variables to local
                            mTitle = response.getString("original_title");
                            mYear = response.getString("release_date");
                            mOverview = response.getString("overview");
                            mRuntime = response.getString("runtime") + " mins";
                            mThumbnail = MOVIE_IMAGE_URI + getScreenDensity() + response.getString("poster_path");
                            mRating = response.getString("vote_average") + "/10";
//
//                            for (int i=0; i<jsonArray.length(); i++) {
//                                JSONObject video = jsonArray.getJSONObject(i);
//
//                                // TODO: Make an array list of trailer items
//                                mTrailerInformation.add(new Media(video.getString("key"),
//                                        "http://img.youtube.com/vi/" + video.getString("key") + "/0.jpg", video.getString("name"), 0));
//                            }

                            // TODO: Recyclerview create
//                            mTrailerRecyclerView.setAdapter(mTrailerAdapter);

                            // TODO: populate the detail fragment
                            populateMovieDetails();

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("JSON", error.getMessage());
                    }
                });

        // Queue the async request
        Volley.newRequestQueue(getActivity()).add(mJsonObjectRequest);
    }


    /*
     * Populate the onscreen controls once the data has been downloaded
     */
    private void populateMovieDetails() {

        // Ensure the rootview is valid before accessing
        if (mRootview != null) {

            // Get reference to the UI controls
            ((TextView) mRootview.findViewById(R.id.medium_detail_title)).setText(mTitle);
            ((TextView) mRootview.findViewById(R.id.medium_detail_releasedt)).setText(mYear);
            ((TextView) mRootview.findViewById(R.id.medium_detail_plot)).setText(mOverview);
            ((TextView) mRootview.findViewById(R.id.medium_detail_rating)).setText(mRating);

            // Use Picasso to output an image - use placeholder where necessary
            Picasso.with(getActivity())
                    .load(mThumbnail)
                    .placeholder(movie_placeholder)
                    .into(((ImageView) mRootview.findViewById(R.id.medium_detail_poster)));

        }
    }


}
