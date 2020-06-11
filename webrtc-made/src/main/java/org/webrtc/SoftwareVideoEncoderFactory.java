/*
 *  Copyright 2017 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.webrtc;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoftwareVideoEncoderFactory implements VideoEncoderFactory {
  @Nullable
  @Override
  public VideoEncoder createEncoder(VideoCodecInfo info) {
    Logging.w("SoftwareVideoEncoderFactory", "end createEncoder info.name:" + info.name);
    if (info.name.equalsIgnoreCase("VP8")) {
      return new LibvpxVp8Encoder();
    }
    if (info.name.equalsIgnoreCase("VP9") && LibvpxVp9Encoder.nativeIsSupported()) {
      return new LibvpxVp9Encoder();
    }
    if (info.name.equalsIgnoreCase("H264") && LibvpxH264Encoder.nativeIsSupported()) {
      return new LibvpxH264Encoder();
    }

    return null;
  }

  @Override
  public VideoCodecInfo[] getSupportedCodecs() {
    return supportedCodecs();
  }

  static VideoCodecInfo[] supportedCodecs() {
    List<VideoCodecInfo> codecs = new ArrayList<VideoCodecInfo>();
    Logging.w("SoftwareVideoEncoderFactory", "end supportedCodecs");
    codecs.add(new VideoCodecInfo("VP8", new HashMap<>()));
    if (LibvpxVp9Encoder.nativeIsSupported()) {
      codecs.add(new VideoCodecInfo("VP9", new HashMap<>()));
    }
    if (LibvpxH264Encoder.nativeIsSupported()) {
      codecs.add(new VideoCodecInfo("H264", new HashMap<>()));
    }

    return codecs.toArray(new VideoCodecInfo[codecs.size()]);
  }
}
