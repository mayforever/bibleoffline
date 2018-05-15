package com.mayforever.bibleoffline.content;

import java.util.HashMap;

/**
 * Created by John Aaron C. Valencia on 4/2/2018.
 */

/**
 * Copyright 2018 John Aaron C. Valencia

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class Bible {
    public HashMap<String, Book> getBible() {
        return bible;
    }

    private HashMap<String, Book> bible = null;
    public Bible() {
        this.bible = new HashMap<String, Book>();
    }
}
