import React, { useEffect, useState } from 'react';
import ReactMarkdown from 'react-markdown';

const Rules = ({ client }) => {
    const [rules, setRules] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('/Rules.md')
            .then((res) => res.text())
            .then((text) =>{
                setRules(text);
                setLoading(false);
            })
            .catch(console.error);
    }, []);

    if (loading) return <div className="content">Loading...</div>;

    return (
        <div className="content">
            <ReactMarkdown>{rules}</ReactMarkdown>
        </div>
    );
};

export default Rules;
