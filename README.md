# Code Sharing Platform

A simple application that allows you to share code and set views and time limits.

## Endpoints

### API
1. `GET /api/code/{uuid}` - return JSON with the following fields:
   * `snippetUUID` - UUID (string)
   * `code` - code (string)
   * `header` - title (string)
   * `createDate` - date of creation (string in `yyyy-MM-dd'T'HH-mm-ss` format)
   * `views` - all views count (number)
   * `deleteDate` - date until snippet will be available (string in `yyyy-MM-dd'T'HH-mm-ss` format or null if there is no limit)
   * `viewsLimit` - allowed number of views (number or null if there is no limit)
2. `GET /api/code/all` - return JSON array with all snippets stored in database in the format above

3. `POST /api/code/new` - create new snippet and return it UUID as String.  
RequestBody should be JSON with the following fields:
      * Required: `code`, `header` as string
      * Optional: `viewsLimit`, `minutesLimit` as number
   
### HTML
1. `GET /code/{uuid}` - return page with code snippet
2. `GET /code/new` - return page with submit form
3. `GET /code/all` - return page with all snippets stored in database

## Compile and run

```
git clone https://github.com/Dalvikk/CodeSharingPlatform
cd ./CodeSharingPlatform/
./gradlew.bat bootJar
java -jar ./build/libs/snippet.jar
```
