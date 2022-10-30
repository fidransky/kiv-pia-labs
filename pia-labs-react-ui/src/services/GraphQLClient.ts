import { QueryListRoomsArgs, RoomVo } from "../generated-types";

const baseUri = 'http://localhost:8080/pia-labs/spring/graphql';

// https://stackoverflow.com/a/51636258
function gql(strings: TemplateStringsArray, ...placeholders: string[]) {
    // Build the string as normal, combining all the strings and placeholders:
    let withSpace = strings.reduce((result, string, i) => (result + placeholders[i - 1] + string));
    let withoutSpace = withSpace.replace(/\s\s+/g, ' ');
    return withoutSpace;
}

export async function searchRooms(variables: QueryListRoomsArgs): Promise<Array<RoomVo>> {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    };

    const requestBody = {
        query: gql`query listRooms ($name: String) {
            listRooms (name: $name) {
                id
                name
                messages {
                    id
                }
            }
        }`,
        variables,
    };

    return fetch(baseUri, {
        method: 'POST',
        headers,
        body: JSON.stringify(requestBody),
    })
        .then((response) => response.json())
        .then((requestBody) => requestBody.data.listRooms);
}
