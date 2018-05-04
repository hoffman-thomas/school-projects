package cs445.a4;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 */
public interface VideoEngine {

    /**
     * The abstract methods below are declared as void methods with no parameters. You need to
     * expand each declaration to specify a return type and parameters, as necessary. You also need
     * to include a detailed comment for each abstract method describing its effect, its return
     * value, any corner cases that the client may need to consider, any exceptions the method may
     * throw (including a description of the circumstances under which this will happen), and so on.
     * You should include enough details that a client could use this data structure without ever
     * being surprised or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new video to the system library, avoiding duplicates.
     *
     * <p>If newVideo is not null, the system does not contain newVideo, and this
     * system has available capacity, then addVideo modifies the library so that it contains
     * newVideo. All other videos remain unmodified.
     *
     * <p>If newVideo is null, then addVideo throws NullPointerException without
     * modifying the library.
     *
     * <p>If this library already contains newVideo, then
     * addVideo returns false without modifying the library.
     *
     * @param newVideo The new video to be added to the library
     * @return true if the addition is successful; false if the video already is in the library
     * @throws NullPointerException if the video is null
     */
    public boolean addVideo(Video newVideo)throws NullPointerException;

    /**
     * Removes an existing video from the system library.
     *
     * <p>If the system contains video, removeVideo will modify the system so that it no
     * longer contains video. All other videos remain unmodified.
     *
     * <p>If this set does not contain video, removeVideo will return false without
     * modifying the system. If video is null, then remove throws NullPointerException
     * without modifying the library.
     *
     * @param video The video in the system to be removed
     * @return  true if the removal was successful; false if the video was not removed or
     * the system does not contain video
     * @throws NullPointerException if video is null
     */
    public boolean removeVideo(Video video)throws NullPointerException;

    /**
     * Adds an existing television episode to an existing television series.
     *
     * <p>If the system contains episode and series, addToSeries will add episode to the
     * series. The rest of the system is unmodified. If series already contains episode,
     * addToSeries will return false without modifying the system.
     *
     * <p>If the system does not contain episode and or series, addToSeries will return false without
     * mofifying the system. The episode and series must already exist in the system
     *
     * <p>If series and or episode is null, addToSeries will throw NullPointerException
     *
     * @param episode The TvEpisode to add to the existing series
     * @param series The Tvseries which the TvEpisode is added to
     * @return true if episode was added to series or if the series already contains the episode;
     * false if the series and or the episode do not exist, Or if the episode is already in the series.
     * @throws NullPointerException if the series is null, the episode is null, or both the series and the episode are null
     */
    public boolean addToSeries(TvEpisode episode, TvSeries series) throws NullPointerException;

    /**
     * Removes a television episode from a television series.
     *
     * <p>If the system contains series, series contains episode, and neither are null,
     * removeFromSeries will remove episode from series.
     *
     * <p>If series and or episode are null, removeFromSeries will throw NullPointerException
     *
     * <p>If series does not contain episode or the system does not contain series, the
     * removeFromSeries will return false
     *
     * @param episode The episode to be removed from series
     * @param series The series containing the episode to be removed
     * @return true if the episode was successfully removed from the series;
     * false if the series does not contain episode, the system does not contain series, the system
     * does not contain the episode.
     * @throws NullPointerException If episode and or series is null
     */
    public boolean removeFromSeries(TvEpisode episode, TvSeries series) throws NullPointerException;

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5.
     *
     * <p>If the user has already rated the video, rateVideo throws IllegalArgumentException
     *
     * <p> If any parameter going into rateVideo is null, rateVideo throws
     * NullPointerException
     *
     * <p> If numStars is outside of the range of integer set {1,2,3,4,5}, rateVideo
     * throws IllegalArgumentException
     *
     * @param numStars The integer quantity of stars given to rate the video
     * @param theUser The user who creates the rating
     * @param theVideo The video that recieves the rating
     * @throws NullPointerException If any numStars, theUser, or theVideo are null
     * @throws IllegalArgumentException If the user has already rated the video, the number of stars is
     * not an integer from (inclusive) 1 to 5 (inclusive).
     */
    public void rateVideo(int numStars, User theUser, Video theVideo) throws NullPointerException, IllegalArgumentException;

    /**
     * Clears a user's rating on a video.
     *
     * <p>If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made.
     *
     * <p>If there is an existing rating and the user and the video are not null, clearRating will
     * remove the rating and return true
     *
     * <p>If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @return true if the rating was successfully cleared; false otherwise
     * @throws IllegalArgumentException if the user does not currently have a rating on record for the video
     * @throws NullPointerException if either the user or the video is null
     */
    public boolean clearRating(User theUser, Video theVideo) throws IllegalArgumentException, NullPointerException;

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5. Uses user behavior and interests (previously watched videos and ratings)
     * to predict the rating of an unrated or unwatched video. In the case that the user has no behavior,
     * predictRating will return the average rating for the video from all other users.
     *
     * <p>If the user has no pre-existing behaivor, then the video's rating will be determined by the
     * other users' ratings for that video. predictRating will return the average rating for theVideo
     *
     * <p>If user is null, the video is null, predictRating will return NullPointerException
     *
     * <p>If the video or the user cannot be found in the system, predictRating will return null
     *
     * <p>If theUser has no existing behavior and the video has no average rating, predictRating will return null
     *
     * @param theUser the user that the rating prediction is to be performed with.
     * @param theVideo the video that rating should be predicted.
     * @return prediction, the integer value between 1 and 5 that represents the number of stars for
     * the rating prediction; null if no user behavior and average video rating exist or if theUser
     * or theVideo does not exist
     * @throws NullPointerException theUser and or theVideo is null
     */
    public int predictRating(User theUser, Video theVideo) throws NullPointerException;

    /**
     * Suggests a video for a user based on their predicted ratings and ratings.
     *
     * <p>If the user has no predicted ratings or previously watched and rated videos, suggestVideo will
     * return popular videos among other users.
     *
     * <p>If there are no popular videos among other users and the user has no predicted ratings, suggestVideo will
     * return null
     *
     * <p>If theUser is null, suggestVideo will throw NullPointerException
     *
     * @param theUser the user to reccomend a video to
     * @return reccomendedVideo, the Video that is reccomended based on their predicted ratings if successful; null if
     * there are no popular videos among other users and theUser has no predictedRatings
     * @throws NullPointerException if theUser is null
     */
    public Video suggestVideo(User theUser) throws NullPointerException;


}
