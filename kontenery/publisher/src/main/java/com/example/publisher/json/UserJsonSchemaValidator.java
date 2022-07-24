package com.example.publisher.json;


import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserJsonSchemaValidator {

    InputStream schemaFile= getClass().getResourceAsStream("/json-schema.json");
    JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaFile));

    public UserJsonSchemaValidator() throws FileNotFoundException {
    }

    public void validateJson(String json) throws ValidationException{
            Schema schemaValidator = SchemaLoader.load(jsonSchema);
            JSONObject jsonData = new JSONObject(new JSONTokener(json));
            schemaValidator.validate(jsonData);
    }
}

