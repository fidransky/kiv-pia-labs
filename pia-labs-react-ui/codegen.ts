import type { CodegenConfig } from '@graphql-codegen/cli'

const config: CodegenConfig = {
    schema: '../pia-labs-web/src/main/resources/graphql/api.graphqls',
    generates: {
        './src/generated-types.ts': {
            plugins: ['typescript'],
        },
    },
}
export default config
