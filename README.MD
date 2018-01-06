# Bubbble

Bubbble is Dribbble client for Android.

This app showcases the MVP pattern and Uncle Bob Martin's Clean Architecture approach.

Libraries and tools used:

- Google Support Libraries
- RxJava 2 - for reactive programming
- Dagger 2 - for dependency injection
- Retrofit 2 - for Networking
- Gson - for parsing the JSON responses
- [Moxy] - for MVP pattern implementation
- Glide - for image loading
- Calligraphy - for custom fonts
- Material Drawer - for sliding menu
- Stetho - for debugging
- JUnit - for unit testing
- Mockito - for mocking & stubbing objects
- Espresso - for instrumentation tests

## Roadmap

- [x] Recomended/recently shots screen
- [x] Shot details screen
- [x] User profile screen
- [x] Shot sharing
- [x] Shot image downloading
- [x] Search by shots
- [ ] User authentication
- [ ] Shots liking
- [ ] Comments liking
- [ ] Commenting

# Setup

Replace `dribbble_client_id`, `dribbble_client_secret`, `dribbble_client_access_token` in **gradle.properties** file to yours.  For more information please visit [this page](http://developer.dribbble.com/).

# Screenshots

<img src="https://github.com/ImangazalievM/Bubbble/blob/master/art/screenshot_1.png" width="40%"> <img src="https://github.com/ImangazalievM/Bubbble/blob/master/art/screenshot_2.png" width="40%">

<img src="https://github.com/ImangazalievM/Bubbble/blob/master/art/screenshot_3.png" width="40%"> <img src="https://github.com/ImangazalievM/Bubbble/blob/master/art/screenshot_4.png" width="40%">
# License

```
The MIT License

Copyright (c) 2017 Mahach Imangazaliev

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```