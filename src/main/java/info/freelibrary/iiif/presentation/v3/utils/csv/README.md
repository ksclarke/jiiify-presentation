# jpv3 cli
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

The first thing one might want to do is process a directory of CSV files into a ZIP file of IIIF Presentation 
manifests and collection documents. This can be done with the following command:
```bash
jpv3 -c -i /path/to/csv/file_or_directory
```
This will process all CSV files in the specified input directory and create a ZIP file of IIIF Presentation manifests 
and collection documents in the specified output directory. The `-c` flag indicates that the ZIP file is created 
locally and not sent to a remote IIIF manifest server. The default output is a file named `output.zip` in the current 
working directory. The output file can be renamed by specifying a different output file name with the `-o` flag.

To send a ZIP file of manifests and collection documents to a remote IIIF manifest server, run:
```bash
jpv3 -u -i /path/to/csv/file_or_directory
```
For this to work though, you need to have three environmental settings set:
* `JPV3_HOST`
* `JPV3_USERNAME`
* `JPV3_PASSWORD`

For UCLA's test IIIF manifest server, the `JPV3_HOST` setting should be set to:
`https://test.ingest.iiif.library.ucla.edu`. 

The username and password are the same as those used with the `festerize` command line tool (except, there the names 
are `FESTERIZE_USERNAME` and `FESTERIZE_PASSWORD` instead of `JPV3_USERNAME` and `JPV3_PASSWORD`).

For UCLA's production IIIF manifest server, the `JPV3_HOST` setting should be set to:
`https://ingest.iiif.library.ucla.edu`. The username and password are the same as those used with test IIIF manifest server.

Once a ZIP file has been uploaded, its contents should be able to be seen by accessing the manifest's or collection 
document's IIIF URL (e.g. https://test.ingest.iiif.library.ucla.edu/collections/ark%3A%2F21198%2Fz11g7wqv or
https://test.iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fz1pw65h4).

Keep in mind this project is still in development and the tool is not yet complete. Suggestions and bug reports are 
welcome.

### Contact

If you have any questions or comments, please contact [Kevin S. Clarke](mailto:ksclarke@library.ucla.edu) for 
assistance. Slack also works well if you're on the UCLA or IIIF Slack channels. My username on there is `ksclarke`.
