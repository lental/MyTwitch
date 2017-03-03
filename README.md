# My Twitch

A twitch app built from scratch.  I inspected twitch's API calls through Charles/Network logs and re-purposed them for this application.

I also inspected Twitch's IMA ad usage, and implemented IMA Ad Prerolls into the app

There are three Activities:

* ChannelActivity: a list of the top 25 active streams on Twitch
* APIDemoActivity: a demonstration of how I constructed the m3u8s NOTE: Video will not successfully play unless 'lenkid' is streaming
* PlayerActivity: an implementation of ExoPlayer playback

To make this I used:

*APIs and Libraries*
* Twitch's stream and auth_token APIs
* Google IMA
* ExoPlayer
* gson
* okhttp

*Android features*
* Fragments
* RecyclerView
* Navigation Views
* AsyncTasks
* Services
* Receivers
* SharedPreferences

*Tools*
* Charles


Time:
Session 1: ~5 hours for most of the app
Sesssion 2: 1.5 hours for IMA integration
