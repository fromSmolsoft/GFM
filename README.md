
**GFM** is simple tool meant to automatically reduce number of filter in Gmail and get rid of duplicated filter.
It compares each filter without its `from` field, gets rid off duplicates and concatenate corresponding `from` fields into single filter so it works as before. 
Tool works best if user creates many simple filters in Gmail e.g. `from + label`. 
In my case it reduced n. of filter from 160 to 80. 

## How to use it
1. Gmail : export and delete filters from Gmail (Make sure to **keep the exported file** as back up.)
2. GFM   : process `mailFilters.xml` with tool
3. Gmail : import `mailFilters.xml` into gmail


## How to use it long version
1. Go to gmail > settings > all settings > filters
2. Check all or any filters that are to be processed
3. Bellow filters click export button (exports `mailFilters.xml`) **Always make sure to keep exported file as back up.**
4. Delete above checked filters in gmail.
5. Load `mailFilters.xml` into the app
6. Click process button (check visually if necessary in UI) 
7. Export as `mailFilters.xml`
8.  Go back to gmail > settings > all settings > filters
9.  Import button bellow filters

## Theme
It runs (for now only) dark theme. 

## Licence
GFM is licensed under the  [ GNU Lesser General Public License v2.1 ](https://github.com/fromSmolsoft/GFM/blob/7f1934e27cce7bb18b09dc1b8f18466d8c93854e/LICENSE#L1)
  )
