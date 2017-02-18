# My Twitch

A twitch app built from scratch.  I inspected twitch's API calls through Charles/Network logs and re-purposed them for this application.

There are three Activities:

* ChannelActivity: a list of the top 25 active streams on Twitch
* APIDemoActivity: a demonstration of how I constructed the m3u8s NOTE: Video will not successfully play unless 'lenkid' is streaming
* PlayerActivity: an implementation of ExoPlayer playback

To make this I used:
* Twitch's stream and auth_token APIs
* gson
* okhttp
* RecyclerView
* ExoPlayer
* AsyncTasks
* Navigation Views

