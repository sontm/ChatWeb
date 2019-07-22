const is_date = /^\d{4}-\d\d-\d\dT\d\d:\d\d:\d\d/;

/**
 * Convert a date string in json format to a Date object.
 *
 * @param json - json string format
 * @returns Date object
 */
export function JsonDateDecoder(json) {
    return JSON.parse(json, (key, value) => {
        if (key !== '' && typeof value === 'string' && is_date.test(value)) {
            return new Date(value);
        }
        return value;
    });
}

/**
 * Converted all date string in a json object to a Date object, return the json object
 * which has all member was recursively converted.
 *
 * @param json - json object
 * @returns converted json object
 */
export function decode_date_in_json(json) {
    if (typeof json === 'object') {
        for (var key in json) {
            if (!json.hasOwnProperty(key)) {
                continue;
            }
            var value = json[key];
            if (typeof value === 'object') {
                decode_date_in_json(value);
            } else if (typeof value === 'string' && is_date.test(value)) {
                json[key] = new Date(value);
            }
        }
    }
    return json;
};
