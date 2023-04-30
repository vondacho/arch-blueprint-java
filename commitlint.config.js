module.exports = {
    extends: ['@commitlint/config-conventional'],
    rules: {
        'type-enum': [2, 'always', [
            'feat',
            'add',
            'drop',
            'fix',
            'bump',
            'make',
            'merge',
            'start',
            'stop',
            'optimize',
            'document',
            'refactor',
            'reformat',
            'rearrange',
            'redraw',
            'reword'
        ]],
        'header-max-length': [2, 'always', 80],
    }
};
