package com.example.analyzer.model;

import java.util.HashMap;
import java.util.Map;

public enum BoxType {
    /**
     * Unique container that encapsulates all metadata of a presentation, including information about
     * the various tracks, timing, editing, and other media-specific information necessary for playback.
     * This box is typically located at the beginning of a file and is parsed first by the media player
     * to extract information about the content before playback can begin.
     */
    MOOV,
    /**
     * A container that holds the metadata and media fragments for a specific media track
     * within a fragmented file.
     */
    MOOF,
    /**
     * It contains information such as creation and modification time of the presentation. It tells the video player
     * the total duration, the timescale,the initial play rate, and the initial volume.
     */
    MVHD,
    /**
     * Similar as MVHD but specifically for individual movie fragments.
     */
    MFHD,
    /**
     * Container for all the metadata related to a single media stream, such as video or audio. This includes
     * information such as the encoding format, language, and other stream-specific metadata.
     */
    TRAK,
    /**
     * This box is contained within the moof box and there must be one of these boxes per 'moof'.
     * This box is the container to other metadata boxes, specifically track metadata. Its contains a single tfhd box
     * and can contain multiple trun boxes.
     */
    TRAF,
    /**
     * There can be zero or more fragments to each track and each track fragment can have multple
     * contiguous runs of samples (can think of them as frames). This box sets up the information
     * for the run of samples.
     */
    TFHD,
    /**
     * Contains information about a single run of samples in a track. It specifies the sample duration,
     * sample size, and sample flags for each sample in the run.
     */
    TRUN,
    /**
     * container for user-defined metadata format identification, allowing for greater customization of
     * metadata in the media file format.
     */
    UUID,
    /**
     * A data container for the media data payload, such as audio and video samples.
     * It does not include any header information or metadata, only the actual media content.
     * The size of the MDAT box determines the duration of the media it contains. This box is critical for
     * playing back the media in the file, as it provides the actual audio and video data.
     */
    MDAT;

    /**
     * Mapping of nested box types to their corresponding {@link BoxType} enums.
     * Note: This map does not include all possible nested box types.
     * TODO: Add more nested box types as needed.
     */
    public static final Map<String, BoxType> NESTED_BOX_TYPES = new HashMap<>();


    static {
        NESTED_BOX_TYPES.put("moof", BoxType.MOOF);
        NESTED_BOX_TYPES.put("moov", BoxType.MOOV);
        NESTED_BOX_TYPES.put("traf", BoxType.TRAF);
        NESTED_BOX_TYPES.put("trak", BoxType.TRAK);
    }
}