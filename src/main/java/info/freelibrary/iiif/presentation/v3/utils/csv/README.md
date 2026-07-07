# JPv3 CLI
***

The JPv3 CLI is an experimental tool for working with IIIF Presentation 3 documents. It may, in the future, be spun 
off into its own project.

The latest usage instructions are available from the command line. To view them, run:
```bash
jpv3 --help
```

Alternatively, this README has simple instructions for getting started with the tool. These are intended to provide a 
quick overview, not a comprehensive guide.

### Getting Started

Before running the program, there are two environment variables that need to be set:
* `JPV3_HOST` # This should be the URL of the IIIF manifest server
* `JPV3_IMAGE_SERVER` # This should be the URL of the IIIF image server

For UCLA users testing the application, the values for these should be `https://test.ingest.iiif.library.ucla.edu` and
`https://iiif.library.ucla.edu/iiif/2`. Both variables are required to create manifest and image links in the generated 
manifests and collection documents.

### Running Locally

Once these variables are set, the next step would be to locally process a directory or ZIP file of CSVs. This can be 
done with the following command:
```bash
jpv3 -c -i /path/to/csv/file_or_directory
```
This will process all CSV files in the specified input directory and create a ZIP file of IIIF Presentation manifests 
and collection documents in the specified output directory. The `-c` flag indicates that the ZIP file is created 
locally and not sent to a remote IIIF manifest server. The default output is a file named `output.zip` in the current 
working directory. The output file can be renamed by specifying a different output file name with the `-o` flag. It
would be a good idea to still use the `.zip` extension for the output file so that it is indentified as a ZIP file.

### Uploading to a Remote Server

To send a ZIP file of manifests and collection documents to a remote IIIF manifest server (like Fester), run:
```bash
jpv3 -u -i /path/to/csv/file_or_directory
```
For this to work though, you need to have two additional environment variables set:
* `JPV3_USERNAME`
* `JPV3_PASSWORD`

The username and password are the same as those used with the `festerize` command line tool (except, there the names 
are `FESTERIZE_USERNAME` and `FESTERIZE_PASSWORD` instead of `JPV3_USERNAME` and `JPV3_PASSWORD`).

To send a ZIP file of manifests and collection documents to a remote IIIF manifest server and have it append and/or
overwrite existing manifests and collections, use the `-p` flag. For example:
```bash
jpv3 -p -i /path/to/csv/file_or_directory
```

Like with regular uploads, the `JPV3_USERNAME` and `JPV3_PASSWORD` environment variables must be set (or their values
passed on the command line with the `-U` and `-P` flags). Manifests uploaded with the `-p` flag will overwrite any
existing manifests with the same ID. Collection documents uploaded with the `-p` flag will append their resources to
the existing collection document.

### UCLA Specific Information

As mentioned earlier, UCLA has a test and production IIIF manifest server. These are not open to the public and are
access-limited. The test server is located at `https://test.ingest.iiif.library.ucla.edu` and the production server is
located at `https://iiif.library.ucla.edu/iiif/2`. The username and password are the same as those used with Festerize.

### Known Issues

Keep in mind this project is still in development and the tool is not yet complete. Suggestions and bug reports are
welcome.

### Contact

If you have any questions or comments, please contact [Kevin S. Clarke](mailto:my.github@kevinclarke.info) for
assistance. Slack also works well if you're on the UCLA or IIIF Slack channels. His username on them is `ksclarke`.
