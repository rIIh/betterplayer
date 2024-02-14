'use strict'

const branch = process.env.CI_COMMIT_BRANCH
const host = process.env.CI_SERVER_HOST
const project = process.env.CI_PROJECT_PATH


module.exports = {
    ci: true,
    branches: ["main", "master"],
    repositoryUrl: `git@${host}:${project}.git`,
    tagFormat: "v${version}",
    plugins: [
        [
            "@semantic-release/commit-analyzer",
            {
                "preset": "angular",
                "releaseRules": [
                    { type: "feat", release: "minor" },
                    { type: "patch", release: "patch" },
                    { type: "fix", release: "patch", },
                    { type: "style", release: "patch", },
                    { "scope": "no-release", "release": false }
                ]
            }
        ],
        [
            "@semantic-release/release-notes-generator",
            require('./.changelog')
        ],
        [
            "@semantic-release/exec",
            {
                prepareCmd: "./scripts/replace_version_name.sh ${nextRelease.version}"
            }
        ],
        [
            "@semantic-release/changelog",
            {
                changelogFile: "CHANGELOG.md",
                changelogTitle: "# CHANGELOG"
            }
        ],
        [
            "@semantic-release/gitlab", {
                successComment: false,
                failComment: false,
                failTitle: false,
            }
        ],
        [
            '@semantic-release/git',
            {
                assets: ['CHANGELOG.md', 'pubspec.yaml'],
                message: 'chore(release): ${nextRelease.version} [release]\n\n${nextRelease.notes}',
            },
        ],
    ]
}
